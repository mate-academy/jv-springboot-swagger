package mate.academy.springboot.swagger.controller;

import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductMapper productMapper;

    public ProductController(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @PostMapping
    public ProductResponseDto create(ProductRequestDto requestDto) {
        productMapper.toModel(requestDto);
    }
}
