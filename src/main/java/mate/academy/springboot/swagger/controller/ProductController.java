package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ParseSortAttributeService;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
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
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> productDtoMapper;

    public ProductController(ProductService productService,
                             DtoMapper<ProductRequestDto, ProductResponseDto, Product>
                                     productDtoMapper) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
    }

    @PostMapping
    @ApiOperation("Create a new product.")
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.save(productDtoMapper.toModel(requestDto));
        return productDtoMapper.toDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a product by id.")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productDtoMapper.toModel(requestDto);
        product.setId(id);
        return productDtoMapper.toDto(productService.update(product));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a product by id.")
    public ProductResponseDto get(@PathVariable Long id) {
        return productDtoMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product by id.")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation("Get all products with sorting and pagination.")
    public List<ProductResponseDto> findAllWithPaginationAndSort(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "id") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, count,
                ParseSortAttributeService.parseSortParams(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation("Get all products by price from and to with sorting and pagination.")
    public List<ProductResponseDto> findAllWithPaginationAndSort(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "id") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, count,
                ParseSortAttributeService.parseSortParams(sortBy));
        return productService.findProductByPriceIsBetween(from, to, pageRequest).stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
