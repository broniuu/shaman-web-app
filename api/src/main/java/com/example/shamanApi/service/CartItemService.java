package com.example.shamanApi.service;

import com.example.shamanApi.dto.CartItemDto;
import com.example.shamanApi.exception.ResourceNotFoundException;
import com.example.shamanApi.model.CartItem;
import com.example.shamanApi.model.Discount;
import com.example.shamanApi.model.Dish;
import com.example.shamanApi.model.User;
import com.example.shamanApi.repository.CartItemRepository;
import com.example.shamanApi.repository.DiscountRepository;
import com.example.shamanApi.repository.DishRepository;
import com.example.shamanApi.repository.UserRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Obsługuje operacje związane z pobieraniem, zapisywaniem i usówaniem dań z koszyka i przygotowaniem ich do wywietlenia
 */
@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private  final DiscountRepository discountRepository;

    public CartItemService(CartItemRepository cartItemRepository, DishRepository dishRepository, UserRepository userRepository, DiscountRepository discountRepository) {
        this.cartItemRepository = cartItemRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }
    private double calculatePriceWithDiscount(Discount discount){
        double price = discount.getDish().getPrice();
        double discountValue = discount.getDiscountValue();
        return price - price*discountValue;
    }

    /**
     * Pobiera dania, do wyświetlenia, obliczając przy tym zniżkę
     *
     * @param login                         login właściela koszyka
     * @return                              dania z koszyka
     * @throws ResourceNotFoundException    wyjątek w przypadku braku produktów w koszyku
     */

    @Override
    public List<CartItemDto> findCartItemsWithDiscountPriceByOwnersLogin(String login) throws ResourceNotFoundException {
        List<CartItem> cartItems = (List<CartItem>) cartItemRepository.findAllByCartOwnerLogin(login);
        if (cartItems.isEmpty()) throw new ResourceNotFoundException("Not found CartItems of user " + login);
        return cartItems.stream().map(x -> mapper.map(x, CartItemDto.class)).toList();
    }

    /**
     * Dodaje danie do koszyka
     *
     * @param ownerLogin    login właściela koszyka
     * @param dishId        ID dania dodanego do koszyka
     * @param countOfDish   ilość sztuk dania do zapisania w koszyku
     * @return              nowe danie z koszyka
     */

    @Override
    public CartItemDto upsertCartItem(String ownerLogin, UUID dishId, int countOfDish) {
        CartItem cartItemWithTheSameDishId = cartItemRepository.findByOwnerLoginAndDishId(ownerLogin, dishId).orElse(null);
        if (cartItemWithTheSameDishId == null) {
            User owner = userRepository.findByLogin(ownerLogin);
            Dish dish = dishRepository.findById(dishId).get();
            CartItem cartItem = new CartItem(owner, dish, countOfDish);
            CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
            cartItemRepository.save(cartItem);
            return cartItemDto;
        }
        cartItemWithTheSameDishId.setCountOfDish(countOfDish);
        CartItemDto cartItemDto = mapper.map(cartItemWithTheSameDishId, CartItemDto.class);
        cartItemRepository.save(cartItemWithTheSameDishId);
        return cartItemDto;
    }

    /**
     * Usówa wszystkie sztuki dania z koszyka
     *
     * @param ownerLogin                    login właściciela koszyka
     * @param cartItemId                    ID dania z koszyka
     * @return                              usunięte danie z koszyka
     * @throws ResourceNotFoundException    wyjątek w przypadku nie znaleznia podanego dania
     */
    @Override
    public CartItemDto deleteCartItem(String ownerLogin, UUID cartItemId) throws ResourceNotFoundException {
        CartItem cartItemWithTheSameId = cartItemRepository.findByOwnerLoginAndCartItemId(
                ownerLogin,
                cartItemId).orElseThrow(
                        () -> new ResourceNotFoundException("Not found CartItem of user " + ownerLogin + " with id = " + cartItemId)
        );
        cartItemRepository.deleteByCartItemId(cartItemWithTheSameId.getCartItemId());
        CartItemDto cartItemDto = mapper.map(cartItemWithTheSameId, CartItemDto.class);
        return cartItemDto;
    }

    /**
     * Zwraca konkretne danie z koszyka danego użytkownika
     *
     * @param ownerLogin                    login właściciela koszyka
     * @param cartItemId                    ID dania z koszyka
     * @return                              danie z koszyka
     * @throws ResourceNotFoundException    wyjątek w przypadku nie znaleznia podanego dania
     */
    @Override
    public CartItemDto findUserCartItemById(String ownerLogin, UUID cartItemId) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepository.findByOwnerLoginAndCartItemId(
                ownerLogin,
                cartItemId).orElseThrow(
                        () -> new ResourceNotFoundException("Not found CartItem of user " + ownerLogin + " with id = " + cartItemId)
        );
        CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
        return cartItemDto;
    }

    /**
     * Zwraca odpowiednią stronę z daniami z koszyka danego użytkownika
     *
     * @param login         login właściciela koszyka
     * @param pageNumber    numer strony
     * @return              dania z Koszyka na danej stronie
     */
    @Override
    public Page<CartItemDto> findPaginatedCartItemsByOwnersLogin(String login, int pageNumber) {
        final int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        UUID userId = userRepository.findUserIdByLogin(login);
        int cartItemsCount = cartItemRepository.countByCartOwnerId(userId);
        Page<CartItem> cartItems = cartItemRepository.findAllByCartOwnerId(userId, pageable);
        if (cartItems.isEmpty() || cartItems.getTotalElements() > cartItemsCount)
            throw new ResourceNotFoundException("Not found CartItems of user " + login + "on this page");
        return cartItems.map(x -> mapper.map(x, CartItemDto.class));
    }

    @Override
    public void addSingleItem(String login, UUID dishId) {
        Optional<CartItem> cartItemOrNull = cartItemRepository.findByOwnerLoginAndDishId(login, dishId);
        if (cartItemOrNull.isEmpty()) {
            User owner = userRepository.findByLogin(login);
            Dish dish = dishRepository.findById(dishId).get();
            CartItem newCartItem = new CartItem(owner, dish, 1);
            cartItemRepository.save(newCartItem);
            return;
        }
        CartItem cartItem = cartItemOrNull.get();
        int newCountOfDish = cartItem.getCountOfDish() + 1;
        cartItem.setCountOfDish(newCountOfDish);
        cartItemRepository.save(cartItem);
    }
    @Override
    public void removeSingeItem(String login, UUID dishId) {
        CartItem cartItem = cartItemRepository.findByOwnerLoginAndDishId(login, dishId).orElseThrow(() ->
                new ResourceNotFoundException("This item not exist in shopping cart!")
        );
        int newCountOfDish = cartItem.getCountOfDish() - 1;
        if (newCountOfDish < 1) {
            cartItemRepository.deleteByCartItemId(cartItem.getCartItemId());
            return;
        }
        cartItem.setCountOfDish(newCountOfDish);
        cartItemRepository.save(cartItem);
    }
}
