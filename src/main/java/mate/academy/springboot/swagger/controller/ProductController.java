package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.dto.mapper.GenericMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortingParamParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final GenericMapper<Product, ProductRequestDto, ProductResponseDto> productMapper;
    private final SortingParamParser sortingParamParser;

    public ProductController(
            ProductService productService,
            GenericMapper<Product, ProductRequestDto, ProductResponseDto> productMapper,
            SortingParamParser sortingParamParser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortingParamParser = sortingParamParser;
    }

    @PostMapping
    @ApiOperation("Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.mapToDto(productService.add(productMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                      @RequestBody ProductRequestDto requestDto) {
        return productMapper.mapToDto(
                productService.update(productMapper.mapToModel(requestDto).setId(id)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation("Get all products with pagination and sorting")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value = 20")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value = 0")
            Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam("default value = 'id'")
            String sortBy
    ) {
        Sort sort = sortingParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    @GetMapping("/price")
    @ApiOperation("Get all by price with pagination and sorting")
    public List<ProductResponseDto> getAllByPrice(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value = 20")
            Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value = 0")
            Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value = 'id'")
            String sortBy) {
        Sort sort = sortingParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getProductsByPriceBetween(pageRequest, from, to).stream()
                .map(productMapper::mapToDto)
                .toList();
    }
}
