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
import mate.academy.springboot.swagger.service.DataInjectService;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final ProductMapper productMapper;
    private final DataInjectService dataInjectService;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             DataInjectService dataInjectService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.dataInjectService = dataInjectService;
    }

    @GetMapping("/inject")
    public String inject() {
        dataInjectService.inject();
        return "Done!";
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.create(productMapper.toModel(productRequestDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by 'id'")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by 'id'")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by 'id'")
    public ProductResponseDto updateById(@PathVariable Long id,
                                         @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toResponseDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    List<ProductResponseDto> findAll(@RequestParam (defaultValue = "0")
                                     @ApiParam(value = "default value is '0'") Integer page,
                                     @RequestParam (defaultValue = "20")
                                     @ApiParam(value = "default value is '20'") Integer count,
                                     @RequestParam (defaultValue = "id")
                                     @ApiParam(value = "default value is 'id'") String sortBy) {
        return productService.findAll(page, count, sortBy)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products between the specified price")
    List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
             @RequestParam BigDecimal to,
             @RequestParam (defaultValue = "0")
             @ApiParam(value = "default value is '0'") Integer page,
             @RequestParam (defaultValue = "20")
             @ApiParam(value = "default value is '20'") Integer count,
             @RequestParam (defaultValue = "id")
             @ApiParam(value = "default value is 'id'") String sortBy) {
        return productService.findAllByPriceBetween(from, to,
                        page, count, sortBy)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
