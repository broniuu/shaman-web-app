package com.example.shamanApi.controler;

import com.example.shamanApi.dto.CartItemDto;
import com.example.shamanApi.dto.CheckoutDto;
import com.example.shamanApi.exception.ResourceNotFoundException;
import com.example.shamanApi.model.Discount;
import com.example.shamanApi.service.ICartItemService;
import com.example.shamanApi.service.IDiscountService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import com.example.shamanApi.service.UserService;
import com.google.zxing.WriterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.shamanApi.ReceiptPrinter.PdfPrinter.makePdf;
import static com.example.shamanApi.service.MailService.sendEmail;
import static com.example.shamanApi.security.Utilities.checkUser;

/**
 * Klasa obsługuje endpointy związane z koszykiem użytkownika
 */
@RestController
public class CartItemController {
    String thankYouNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus felis neque, tempus eu aliquam vitae, blandit sed lorem. Morbi condimentum eleifend velit, eu tincidunt orci malesuada sed. Sed quam augue, tempus eu luctus ac, luctus ac libero. Phasellus lobortis, libero eu ultricies porttitor, lorem risus placerat mi, non tempus massa mi ac metus. Sed ultricies elit quis lectus maximus, vitae laoreet enim congue. Etiam rutrum nunc quam, eu venenatis neque egestas sed. In hac habitasse platea dictumst. Pellentesque accumsan, quam elementum ultrices scelerisque, nulla velit pretium mauris, nec dapibus lectus nisi a purus.";
    private final ICartItemService cartItemService;
    private final UserService userService;
    private IDiscountService discountService;

    public CartItemController(ICartItemService cartItemService, UserService userService, IDiscountService discountService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.discountService = discountService;
    }

    /**
     * Wyświetla dania z koszyka danego użytkownika
     *
     * @param login      login właściciela koszyka
     * @param pageNumber numer Strony, którą chcemy wyświetlić
     * @return dania z koszyka
     */
    @GetMapping(value = "{login}/usercart", params = {"p"})
    public ResponseEntity<Iterable<CartItemDto>> getCartItemsByUser(
            @PathVariable String login,
            @RequestParam("p") int pageNumber) {
        if (checkUser(login)) {
            Page<CartItemDto> resultPage = cartItemService.findPaginatedCartItemsByOwnersLogin(login, pageNumber);
            return new ResponseEntity<>(resultPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Wyświetla konkretne danie z koszyka danego użytkownika
     *
     * @param login      login właściciela koszyka
     * @param cartItemId ID dania z koszyka
     * @return danie z koszyka
     */
    @GetMapping("{login}/usercart/{cartItemId}")
    public ResponseEntity<CartItemDto> getUserCartItemByUserAndCartItemId(@PathVariable String login, @PathVariable UUID cartItemId) {
        if (checkUser(login)) {
            return new ResponseEntity<>(cartItemService.findUserCartItemById(login, cartItemId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Dodaje danie do koszyka
     *
     * @param login  login właściciela koszyka
     * @param dishId ID dania
     * @param count  ilość sztuk dania do zapisania w koszyku
     * @return nowe danie z koszyka
     */
    @PostMapping("{login}/usercart/{dishId}/save/{count}")
    public ResponseEntity<CartItemDto> upsertCartItem(@PathVariable String login, @PathVariable UUID dishId, @PathVariable int count) {
        if (checkUser(login)) {
            CartItemDto cartItemDto = cartItemService.upsertCartItem(login, dishId, count);
            return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Usówa wszystkie sztuki dania z koszyka
     *
     * @param login      login właściciela koszyka
     * @param cartItemId ID dania z koszyka
     * @return usunięte danie z koszyka
     */
    @DeleteMapping("{login}/usercart/{cartItemId}/delete")
    public ResponseEntity<CartItemDto> deleteCartItem(@PathVariable String login, @PathVariable UUID cartItemId) {
        if (checkUser(login) || userService.checkRoleOfLoggedUser(login)) {
            CartItemDto cartItemDto = cartItemService.deleteCartItem(login, cartItemId);
            return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * testowe wyświetlenie paragonu, bez usuwania zawartości z koszyka ani wysyłania maila
     *
     * @param login login właściciela koszyka
     * @return komunikat o udanej transakcji
     * @throws IOException
     * @throws WriterException
     */
    @GetMapping("{login}/usercart/getpdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable String login) throws IOException, WriterException {
        boolean outdatedEndpoint = true;
        if (outdatedEndpoint) {
            throw new NotImplementedException("Endpoint wyłączony z użtyku");
        }
        if (checkUser(login)) {
            List<CartItemDto> cartItemDtos = cartItemService.findCartItemsWithDiscountPriceByOwnersLogin(login);
            if (cartItemDtos == null)
                throw new ResourceNotFoundException("The usercart is empty!");
            var headers = prepareHeadersForPdf();
//            ByteArrayOutputStream rawPdf=makePdf(userService.showUserAccount(login),cartItemDtos, true,"test");
//            ResponseEntity<byte[]> pdf = new ResponseEntity<byte[]>(rawPdf.toByteArray(), headers, HttpStatus.OK);
//            return pdf;
            return new ResponseEntity<>(new byte[]{}, HttpStatus.GONE);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }


    }

    /**
     * Dokonuje transackji, czyści koszyk, wyswietla paragon oraz wysyła go na maila
     *
     * @param login login właściciela koszyka
     * @return komunikat o udanej transakcji
     * @throws IOException
     * @throws WriterException
     */
    @PostMapping("{login}/usercart/checkout")
    public ResponseEntity<byte[]> checkout(@PathVariable String login, @RequestBody CheckoutDto body) throws IOException, WriterException {
        if (checkUser(login)) {
            String note=body.getNote();
            String discountCode=body.getDiscountCode();
            boolean delivery=body.getDelivery();
            List<CartItemDto> cartItemDtos = cartItemService.findCartItemsWithDiscountPriceByOwnersLogin(login);
            if (cartItemDtos == null)
                throw new ResourceNotFoundException("The usercart is empty!");
            var headers = prepareHeadersForPdf();
            ByteArrayOutputStream rawPdf = null;
            if(!discountCode.equals("") || !discountCode.isEmpty()){
                var discount = discountService.saveUsedDiscountWithItsOwner(discountCode, login, cartItemDtos);
                var savedMoney = calculateSavedMoney(discount, cartItemDtos);
                 rawPdf = makePdf(userService.showUserAccount(login), cartItemDtos, delivery, note, savedMoney);
            }else{
                rawPdf = makePdf(userService.showUserAccount(login), cartItemDtos,delivery, note,0);
            }
            ResponseEntity<byte[]> pdf = new ResponseEntity<byte[]>(rawPdf.toByteArray(), headers, HttpStatus.OK);
            cartItemDtos.forEach((cartItem) -> cartItemService.deleteCartItem(login, cartItem.getCartItemId()));
            Date date = new Date();
            sendEmail(rawPdf, userService.showUserAccount(login).getEmail(), "Receipt from:" + date, thankYouNote);
            return pdf;
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    private double calculateSavedMoney(Discount discount, List<CartItemDto> cartItemDtos){
        var countOfDishesWithThisDiscount = cartItemDtos.stream()
                .filter(x -> x.getDish().getDishId().equals(discount.getDishId()))
                .count();
        var moneySavedFromOneDish = discount.getDish().getPrice() * discount.getDiscountValue();
        return moneySavedFromOneDish * countOfDishesWithThisDiscount;
    }

    private HttpHeaders prepareHeadersForPdf() {
        HttpHeaders headers = new HttpHeaders();
        String filename = "Rachunek.pdf";
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }

    @PostMapping("{login}/usercart/{dishId}/add")
    public ResponseEntity addDishToUserCart(@PathVariable String login, @PathVariable UUID dishId) {
        if (checkUser(login)) {
            cartItemService.addSingleItem(login, dishId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("{login}/usercart/{dishId}/remove")
    public ResponseEntity removeDishFromUserCart(@PathVariable String login, @PathVariable UUID dishId) {
        if (checkUser(login)) {
            cartItemService.removeSingeItem(login, dishId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

}
