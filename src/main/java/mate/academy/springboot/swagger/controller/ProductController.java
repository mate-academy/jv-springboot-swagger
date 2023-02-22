package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.Mapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.PageRequestPrepare;
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
    private ProductService productService;
    private Mapper<Product, ProductRequestDto, ProductResponseDto> mapper;

    public ProductController(ProductService productService, Mapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(mapper.toModel(requestDto));
        return mapper.toResponseDto(product);
    }

    @PutMapping
    public ProductResponseDto update(@PathVariable Long id,
                          @RequestBody ProductRequestDto requestDto) {
        Product product = productService.getById(id);
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return mapper.toResponseDto(productService.save(product));
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toResponseDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer size,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {

        return productService.getAll(PageRequestPrepare.getPageRequestObj(size, page, sortBy))
                .stream()
                .map(p -> mapper.toResponseDto(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal priceFrom,
                                                          @RequestParam BigDecimal priceTo) {
        return productService.findAllByPriceBetween(priceFrom, priceTo)
                .stream()
                .map(p -> mapper.toResponseDto(p))
                .collect(Collectors.toList());
    }
}
