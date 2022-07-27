package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapperDto;
import mate.academy.springboot.swagger.service.sorting.CustomSortingOrderBy;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "20";
    private final ProductService productService;
    private final ProductMapperDto productMapperDto;
    private final CustomSortingOrderBy sortingOrderBy;

    @Autowired
    public ProductController(ProductService productService,
                             ProductMapperDto productMapperDto,
                             CustomSortingOrderBy sortingOrderBy) {
        this.productService = productService;
        this.productMapperDto = productMapperDto;
        this.sortingOrderBy = sortingOrderBy;
    }

    @PostMapping
    @ApiOperation(value = "Create the Product model.")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapperDto.mapToModel(requestDto);
        return productMapperDto.mapToDto(productService.create(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get the Product model by id.")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapperDto.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the Product model by id.")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update the Product model.")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapperDto.mapToModel(requestDto);
        return productMapperDto.mapToDto(productService.update(id, product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products with pagination and sorting.")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = SIZE_DEFAULT_VALUE)
            @ApiParam(value = "Default size is 20") Integer size,
            @RequestParam(defaultValue = PAGE_DEFAULT_VALUE)
            @ApiParam(value = "Default page is 0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sortingOrderBy.sortBy(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(productMapperDto::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-by-price")
    @ApiOperation(value = "Get all products where price is between two values "
            + "and ability to pagination and sorting.")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam(defaultValue = SIZE_DEFAULT_VALUE)
            @ApiParam(value = "Default size is 20") Integer size,
            @RequestParam(defaultValue = PAGE_DEFAULT_VALUE)
            @ApiParam(value = "Default page is 0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sortingOrderBy.sortBy(sortBy));
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapperDto::mapToDto)
                .collect(Collectors.toList());
    }
}
