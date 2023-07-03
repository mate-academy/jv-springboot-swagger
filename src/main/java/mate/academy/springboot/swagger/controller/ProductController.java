package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.product.ProductRequestDto;
import mate.academy.springboot.swagger.dto.product.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortUtil;
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
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;

    public ProductController(ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product>
                                     productResponseDtoMapper) {
        this.productService = productService;
        this.productRequestDtoMapper = productRequestDtoMapper;
        this.productResponseDtoMapper = productResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productRequestDtoMapper
                .mapToModel(productRequestDto));
        return productResponseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product from DB by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productResponseDtoMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productRequestDtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productResponseDtoMapper.mapToDto(product);
    }

    @GetMapping("/search-by-price")
    @ApiOperation(value = "Find all products in range. "
            + "And sorted result by criteria")
    public List<ProductResponseDto> getProductsWhenPriceBetween(@RequestParam BigDecimal from,
                                @RequestParam BigDecimal to,
                                @RequestParam (defaultValue = "10")
                                @ApiParam(value = "default value is 10") Integer size,
                                @RequestParam (defaultValue = "0")
                                @ApiParam(value = "default value is 0") Integer page,
                                @RequestParam(defaultValue = "name")
                                @ApiParam(value = "default value is 'name'") String sortBy) {
        Sort sort = SortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAllByPriceBetween(pageRequest, from, to)
                .stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    @ApiOperation(value = "Find and sort all products from DB")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "10")
                                @ApiParam(value = "default value is 10") Integer size,
                                @RequestParam (defaultValue = "0")
                                @ApiParam(value = "default value is 0") Integer page,
                                @RequestParam (defaultValue = "name")
                                @ApiParam(value = "default value is 'name'") String sortBy) {
        Sort sort = SortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAllProducts(pageRequest)
                .stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
