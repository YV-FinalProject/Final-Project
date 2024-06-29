package com.example.finalproject.service;

import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @Mock
    private CartRepository cartRepositoryMock;

    @Mock
    public CartItemRepository cartItemRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private ProductService productServiceMock;

    @InjectMocks
    private CartService cartServiceMock;

    DataNotFoundInDataBaseException dataNotFoundInDataBaseException;
    InvalidValueExeption invalidValueExeption;

    private User user;
    private Cart cart;
    private CartItem cartItem;
    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto, wrongProductRequestDto;
    private Product product, productToInsert;
    private Category category;
    private CartItemResponseDto cartItemResponseDto;
    private CartResponseDto cartResponseDto;
    private UserResponseDto userResponseDto;
    private CartItemRequestDto cartItemRequestDto, wrongCartItemRequestDto;

    @BeforeEach
    void setUp() {

        user = new User(1L,
                "Arne Oswald",
                "arneoswald@example.com",
                "+496151226",
                "Pass1$trong",
                Role.CLIENT,
                null,
                null,
                null);

        cart = new Cart(1L, null, user);


        product = new Product(1L,
                "Name",
                "Description",
                new BigDecimal("100.00"),
                new BigDecimal("0.00"),
                "http://localhost/img/1.jpg",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                new Category(1L, "Category", null),
                null,
                null,
                null);

        cartItem = new CartItem(1L, product, 2, cart);
        Set<CartItem> cartItemSet = new HashSet<>();
        cartItemSet.add(cartItem);
        cart.setCartItems(cartItemSet);
        user.setCart(cart);

        userResponseDto = UserResponseDto.builder()
                .userID(1L)
                .name("Arne Oswald")
                .email("arneoswald@example.com")
                .phone("+496151226")
                .password("Pass1$trong")
                .role(Role.CLIENT)
                .build();

        cartResponseDto = CartResponseDto.builder()
                .cartId(1L)
                .userResponseDto(userResponseDto)
                .build();
//
//        productResponseDto = ProductResponseDto.builder()
//                .productId(1L)
//                .name("Name")
//                .description("Description")
//                .price(new BigDecimal("100.00"))
//                .imageURL("http://localhost/img/1.jpg")
//                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
//                .categoryResponseDto(new CategoryResponseDto(1L, "Category"))
//                .build();
//
        cartItemResponseDto = CartItemResponseDto.builder()
                .cartItemId(1L)
                .cartResponseDto(cartResponseDto)
                .productResponseDto(productResponseDto)
                .quantity(5)
                .build();

        productRequestDto = ProductRequestDto.builder()
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .discountPrice(new BigDecimal("0.00"))
                .imageURL("http://localhost/img/1.jpg")
                .category("Category")
                .build();

        cartItemRequestDto = CartItemRequestDto.builder()
                .productId(1L)
                .quantity(5)
                .build();

        wrongCartItemRequestDto = CartItemRequestDto.builder()
                .productId(66L)
                .quantity(5)
                .build();
    }

    @Test
    void getCartItemsByUserId() {
        Long id = 1L;
        Long wrongId = 58L;

        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(user));
        when(mappersMock.convertToCartItemResponseDto(any(CartItem.class))).thenReturn(cartItemResponseDto);
        Set<CartItemResponseDto> cartItemRsponseDtoSet = new HashSet<>();
        cartItemRsponseDtoSet.add(cartItemResponseDto);
        Set<CartItemResponseDto> actualCartItemSet = cartServiceMock.getCartItemsByUserId(id);

        verify(userRepositoryMock, times(1)).findById(id);
        verify(mappersMock, times(1)).convertToCartItemResponseDto(any(CartItem.class));

        assertFalse(actualCartItemSet.isEmpty());
        assertEquals(cartItemRsponseDtoSet.size(), actualCartItemSet.size());
        assertEquals(cartItemRsponseDtoSet.hashCode(), actualCartItemSet.hashCode());

        when(userRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> cartServiceMock.getCartItemsByUserId(wrongId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void insertCartItem() {
        Long id = 1L;
        Long wrongId = 58L;

        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(user));
        when(productRepositoryMock.findById(cartItemRequestDto.getProductId())).thenReturn(Optional.of(product));
        when(cartRepositoryMock.findById(user.getCart().getCartId())).thenReturn(Optional.of(cart));
        CartItem cartItemToInsert = new CartItem();
        cartItemToInsert.setCart(cart);
        cartItemToInsert.setCartItemId(0L);
        cartItemToInsert.setProduct(product);
        cartItemToInsert.setQuantity(cartItemRequestDto.getQuantity());

        cartServiceMock.insertCartItem(cartItemRequestDto, id);

        verify(cartItemRepositoryMock, times(1)).save(any(CartItem.class));

        when(userRepositoryMock.findById(wrongId)).thenReturn(Optional.empty());
        when(productRepositoryMock.findById(wrongCartItemRequestDto.getProductId())).thenReturn(Optional.empty());

        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> cartServiceMock.insertCartItem(wrongCartItemRequestDto, id));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());

        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> cartServiceMock.insertCartItem(cartItemRequestDto, wrongId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void deleteCarItemByProductId() {

        Long userId = 1L;
        Long wrongUserId = 75L;

        Long productId = 1L;
        Long wrongProductId = 75L;

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(product));
        Set<CartItem> cartItemSet = user.getCart().getCartItems();

        cartServiceMock.deleteCarItemByProductId(userId, productId);

        verify(userRepositoryMock, times(1)).findById(userId);
        verify(productRepositoryMock, times(1)).findById(productId);

        for (CartItem item : cartItemSet) {
            verify(cartItemRepositoryMock, times(1)).delete(item);
        }


        when(userRepositoryMock.findById(wrongUserId)).thenReturn(Optional.empty());
        when(productRepositoryMock.findById(wrongProductId)).thenReturn(Optional.empty());

        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> cartServiceMock.deleteCarItemByProductId(userId, wrongUserId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());

        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> cartServiceMock.deleteCarItemByProductId(wrongUserId, productId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }
}