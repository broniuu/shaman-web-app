package com.example.shamanApi.service;

import com.example.shamanApi.dto.CartItemDto;
import com.example.shamanApi.dto.DiscountDto;
import com.example.shamanApi.dto.DiscountToViewDto;
import com.example.shamanApi.dto.DishWithDiscountDto;
import com.example.shamanApi.exception.DishNotFoundException;
import com.example.shamanApi.exception.ResourceNotFoundException;
import com.example.shamanApi.extension.DiscountExtension;
import com.example.shamanApi.model.Discount;
import com.example.shamanApi.model.User;
import com.example.shamanApi.repository.DiscountRepository;
import com.example.shamanApi.repository.UserRepository;
import lombok.experimental.ExtensionMethod;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

/**
 * Obsługuje operacje związane z zarządzaniem zniżkami
 */
@Service
@Transactional
@ExtensionMethod({DiscountExtension.class})
public class DiscountService implements IDiscountService {
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public DiscountService(DiscountRepository discountRepository, UserRepository userRepository) {
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    /**
     * Pobiera wszystkie zniżki na danej stronie
     *
     * @param pageNumber numer strony
     * @return wszystkie zniżki na danej stronie
     */
    @Override
    public Page<DiscountToViewDto> findDiscountDtos(int pageNumber) {
        final int pageSize = 10;
        List<Discount> discounts = (List<Discount>) discountRepository.findAll();
        int discountsTotal = discounts.size();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Discount> discountPage = new PageImpl<>(discounts, pageable, discountsTotal);
        if (discounts.isEmpty() || discountPage.getTotalElements() > discountsTotal)
            throw new DishNotFoundException("There is no discounts on this page");
        return discountPage.map(discount -> discount.toDiscountDto());
    }

    @Override
    public DishWithDiscountDto tryUnlockDiscount(String discountCode, String login) {
        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new RuntimeException("Podano nieporpawny Kod Rabatowy"));
        User user = userRepository.findByLogin(login);
        if (user == null)
            throw new RuntimeException("Użytkownik o podanym loginie nie istnieje!");
        UUID discountId = discount.getDiscountId();
        throwExceptionIfDiscountWasUsedByThisUser(discountId, user);
        if (discount.getDish() == null){
            throw new RuntimeException("Nie istnieje danie z tą zniżką!");
        }
        return discountToDiscountDto(discount);
    }
    @Override
    public List<DiscountDto> getDiscountsOfUser(String userLogin) {
        List<Discount> discounts = discountRepository.findAllByUsersWhoUsedThisDiscount(userLogin);
        List<DiscountDto> discountDtos = discounts.stream().map(x -> mapper.map(x, DiscountDto.class)).toList();
        if (discountDtos.size() == 0) throw new ResourceNotFoundException("Nie znaleziono zniżek dla tego użytkownika");
        return discountDtos;
    }
    @Override
    public Discount saveUsedDiscountWithItsOwner(String discountCode, String ownersLogin, List<CartItemDto> cartItemDtos) {
        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new RuntimeException("Zniżka o podanym kodzie nie istnieje"));
        boolean discountNotCauseAnyDishes = cartItemDtos.stream()
                .noneMatch(x -> {
                    var dishId = x.getDish().getDishId();
                    var discountDishId = discount.getDishId();
                    return dishId.equals(discountDishId);
                });
        if (discountNotCauseAnyDishes)
            throw new RuntimeException("Zniżka nie dotyczy żadnego z wybranych produktów!");
        User discountOwner = userRepository.findByLogin(ownersLogin);
        if (discountOwner == null)
            throw new RuntimeException("Ten użytkownik nie istnieje");
        throwExceptionIfDiscountWasUsedByThisUser(discount.getDiscountId(), discountOwner);
        List<User> usersWithThisDiscount = discount.getUsersWhoUsedThisDiscount();
        usersWithThisDiscount.add(discountOwner);
        discount.setUsersWhoUsedThisDiscount(usersWithThisDiscount);
        discountRepository.save(discount);
        return discount;
    }
    private void throwExceptionIfDiscountWasUsedByThisUser(UUID discountId, User user) {
        boolean discountWasUsedByThisUser = discountRepository.existsByDiscountIdAndUsersWhoUsedThisDiscount(discountId, user);
        if (discountWasUsedByThisUser) {
            throw new RuntimeException("Użytkownik już wykorzystał tą zniżkę!");
        }
    }

    private DishWithDiscountDto discountToDiscountDto(Discount discount){
        DishWithDiscountDto dishWithDiscountDto = new DishWithDiscountDto();
        dishWithDiscountDto.setDishId(discount.getDishId());
        dishWithDiscountDto.setName(discount.getDish().getName());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double oldPrize = discount.getDish().getPrice();
        String formattedOldPrize = decimalFormat.format(oldPrize);
        dishWithDiscountDto.setOldPrice(formattedOldPrize);
        double discountValue = discount.getDiscountValue();
        dishWithDiscountDto.setPercentageDiscount(discountValue);
        double moneySavedOnDiscount = oldPrize * discountValue;
        String formattedMoneySavedOnDiscount = decimalFormat.format(moneySavedOnDiscount);
        dishWithDiscountDto.setSavedMoney(formattedMoneySavedOnDiscount);
        double newPrize = oldPrize - moneySavedOnDiscount;
        String formattedNewPrize = decimalFormat.format(newPrize);
        dishWithDiscountDto.setNewPrice(formattedNewPrize);
        return dishWithDiscountDto;
    }
}
