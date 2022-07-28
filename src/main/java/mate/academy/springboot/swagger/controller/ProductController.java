package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponceDto;
import mate.academy.springboot.swagger.service.CustomSorting;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "20";
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CustomSorting customSorting;

    public ProductController(ProductService productService, ProductMapper productMapper, CustomSorting customSorting) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.customSorting = customSorting;
    }

    @PostMapping
    @ApiOperation(value = "Save product to DB")
    public ProductResponceDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper
                .mapToDto(productService.save(productMapper.mapToModel(productRequestDto)));
    }

    @GetMapping("/id")
    @ApiOperation("Get product by id")
    public ProductResponceDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/id")
    @ApiOperation("Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping
    @ApiOperation("Update product in DB")
    public ProductResponceDto update(@RequestBody ProductRequestDto requestDto) {
        return productMapper
                .mapToDto(productService.save(productMapper.mapToModel(requestDto)));
    }


}
