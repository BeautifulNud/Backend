package ggudock.domain.cart.application;

import ggudock.domain.cart.application.dto.CartResponse;
import ggudock.domain.cart.entity.Cart;
import ggudock.domain.cart.repository.CartRepository;
import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public void saveCart(String email, Long itemId) {
        User user = getUser(email);
        Item item = getItem(itemId);
        checkCart(email, itemId);
        Cart cart = createCart(user, item);
        save(cart);
    }

    private void checkCart(String email, Long itemId) {
        if(cartRepository.existsByUser_EmailAndItem_Id(email, itemId)){
            throw new BusinessException(ErrorCode.DUPLICATED_CART);
        }
    }

    public void deleteCart(Long cartId) {
        delete(getCart(cartId));
    }

    public CartResponse getDetail(Long cartId) {
        return createCartResponse(cartId);
    }

    public List<CartResponse> getAll(String email) {
        return createCartList(email).stream().map(CartResponse::new).collect(Collectors.toList());
    }

    private List<Cart> createCartList(String email) {
        return cartRepository.findAllByUser_Email(email);
    }

    private CartResponse createCartResponse(Long cartId) {
        Cart cart = getCart(cartId);
        return new CartResponse(cart);
    }

    private Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CART));
    }


    private void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    private Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    private static Cart createCart(User user, Item item) {
        return Cart.builder()
                .user(user)
                .item(item)
                .build();
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }
}
