package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;

    @Autowired
    public ProductController(
            ProductService productService,
            ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper,
            RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper) {
        this.productService = productService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto requestProductDto) {
        return responseDtoMapper.toDto(
                productService.save(requestDtoMapper.toModel(requestProductDto)));
    }

    @GetMapping("/{product_id}")
    public ProductResponseDto getById(@PathVariable(name = "product_id") Long productId) {
        return responseDtoMapper.toDto(productService.getById(productId));
    }

    @DeleteMapping
    public void deleteById(@RequestParam(name = "product_id") Long productId) {
        productService.delete(productService.getById(productId));
    }

    @PutMapping
    public ProductResponseDto update(@RequestParam(name = "product_id") Long productId,
                                     @RequestBody ProductRequestDto requestProductDto) {
        Product product = requestDtoMapper.toModel(requestProductDto);
        product.setId(productId);
        return responseDtoMapper.toDto(productService.save(product));
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll().stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    public List<ProductResponseDto> findAllByPrice(@RequestParam BigDecimal from,
                                                   @RequestParam BigDecimal to) {
        return productService.findAllByPriceBetween(from, to).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
