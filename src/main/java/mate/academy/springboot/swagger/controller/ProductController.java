package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "create a new Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get Product by ID")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productMapper.toResponseDto(product);
    }

    @DeleteMapping
    @ApiOperation(value = "delete Product by ID")
    public void delete(@RequestParam Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "update Product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "get all products with pagination and ability to sort by price or by "
            + "title in ASC or DESC order")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "8")
                                            @ApiParam(value = "default value is 8") Integer count,
                                            @RequestParam (defaultValue = "0")
                                            @ApiParam(value = "default value is 0") Integer page,
                                            @RequestParam (defaultValue = "price")
                                            @ApiParam(value = "default value is price")
                                                       String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAll(pageRequest, sortBy).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/prices")
    @ApiOperation(value = "get all products where price is between two values received as a "
            + "RequestParam inputs. Add pagination and ability to sort by price or by title in ASC "
            + "or DESC order.")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal firstPrice,
                                                          @RequestParam BigDecimal secondPrice,
                                                          @RequestParam (defaultValue = "8")
                                                          @ApiParam(value = "default value is 8")
                                                                     Integer count,
                                                          @RequestParam (defaultValue = "0")
                                                          @ApiParam(value = "default value is 0")
                                                                     Integer page,
                                                          @RequestParam (defaultValue = "price")
                                                          @ApiParam(value = "default value is "
                                                                  + "price")
                                                                      String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAllByPriceBetween(firstPrice,secondPrice, pageRequest, sortBy)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
