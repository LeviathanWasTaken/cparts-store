package com.leviathan.manager.controller;

import com.leviathan.manager.client.BadRequestException;
import com.leviathan.manager.client.ProductsRestClient;
import com.leviathan.manager.controller.payload.NewProductPayload;
import com.leviathan.manager.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductsController module tests")
class ProductsControllerTest {

    @Mock
    ProductsRestClient productsRestClient;

    @InjectMocks
    ProductsController controller;

    @Test
    @DisplayName("createProduct should create new product and redirect to created product page")
    void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
        //given
        var payload = new NewProductPayload("New Product", "New Product Description");
        var model = new ConcurrentModel();

        doReturn(new Product(1, "New Product", "New Product Description"))
                .when(productsRestClient)
                .createProduct(notNull(), any());
        //when
        var result = this.controller.createProduct(payload, model);
        //then
        assertEquals("redirect:/catalogue/products/1", result);

        verify(this.productsRestClient).createProduct(notNull(), any());
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("createProduct should return error page if request is not valid")
    void createProduct_RequestIsInvalid_ReturnsProductFormWithErrors() {
        //given
        var payload = new NewProductPayload("-", null);
        var model = new ConcurrentModel();

        doThrow(new BadRequestException(List.of("error 1", "error 2")))
                .when(productsRestClient)
                .createProduct("-", null);
        //when
        var result = this.controller.createProduct(payload, model);
        //then
        assertEquals("catalogue/products/new_product", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("error 1", "error 2"), model.getAttribute("errors"));

        verify(this.productsRestClient).createProduct("-", null);
        verifyNoMoreInteractions(this.productsRestClient);
    }
}