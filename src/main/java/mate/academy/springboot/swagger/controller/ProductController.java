package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponceDto;
import mate.academy.springboot.swagger.service.CustomSorting;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    private final ProductMapper productMapper;
    private final CustomSorting customSorting;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             CustomSorting customSorting) {
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

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ProductResponceDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
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

    @GetMapping("/price")
    @ApiOperation(value = "Get all products with price between two values "
            + "and ability to pagination and sorting")
    public List<ProductResponceDto> getAllByPriceBetween(
            @RequestParam(defaultValue = SIZE_DEFAULT_VALUE)
            @ApiParam(value = "Default size is 20") Integer size,
            @RequestParam(defaultValue = PAGE_DEFAULT_VALUE)
            @ApiParam(value = "Default page is 0") Integer page,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam BigDecimal priceFrom,
            @RequestParam BigDecimal priceTo) {
        PageRequest pageRequest = PageRequest.of(page, size, customSorting.sortBy(sortBy));
        return productService.findAllByPriceBetween(priceFrom, priceTo, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Get all products with pagination and sorting")
    public List<ProductResponceDto> getAll(@RequestParam(defaultValue = SIZE_DEFAULT_VALUE)
                                           @ApiParam(value = "Default size is 20") Integer size,
                                           @RequestParam(defaultValue = PAGE_DEFAULT_VALUE)
                                           @ApiParam(value = "Default page is 0") Integer page,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, customSorting.sortBy(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
