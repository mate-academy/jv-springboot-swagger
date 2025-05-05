package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductDtoMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
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
    private final ProductDtoMapper productDtoMapper;

    public ProductController(ProductService productService, ProductDtoMapper productDtoMapper) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return productDtoMapper.toDto(productService.save(
                productDtoMapper.toModel(productRequestDto)));
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Update an existing product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productDtoMapper.toModel(productRequestDto);
        product.setId(id);
        return productDtoMapper.toDto(productService.save(product));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productDtoMapper.toDto(productService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete a product by id")
    public boolean deleteById(@PathVariable Long id) {
        productService.deleteDyId(id);
        return true;
    }

    @GetMapping
    @ApiOperation(value = "Get all products with paging and sorting")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "10")
            @ApiParam(value = "products per page, default value is 10") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "page number, default value is 0") Integer page,
            @RequestParam (defaultValue = "price")
            @ApiParam(value = "sorting, default value is DESC") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, SortUtil.getSort(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/price")
    @ApiOperation(value = "Get all products in a certain price range with paging and sorting")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam
            @ApiParam(value = "Price from") String from,
            @RequestParam
            @ApiParam(value = "Price to") String to,
            @RequestParam (defaultValue = "10")
            @ApiParam(value = "products per page, default value is 10") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "page number, default value is 0") Integer page,
            @RequestParam (defaultValue = "price")
            @ApiParam(value = "sorting, default value is DESC") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, SortUtil.getSort(sortBy));
        return productService.findAllByPriceBetween(
                new BigDecimal(from), new BigDecimal(to), pageRequest)
                .stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
