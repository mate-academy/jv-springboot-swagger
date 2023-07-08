package mate.academy.springboot.swagger.controller;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void findAll_Ok() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product test");
        product.setPrice(BigDecimal.valueOf(100));

        List<Product> mockProduct = List.of(product);
        Mockito.when(productService.findAll(10, 0, "id"))
                .thenReturn(mockProduct);
        RestAssuredMockMvc
                .given()
                .queryParam("page", 10)
                .queryParam("count", 0)
                .queryParam("sortBy", "id")
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].price", Matchers.equalTo(100));
    }
}