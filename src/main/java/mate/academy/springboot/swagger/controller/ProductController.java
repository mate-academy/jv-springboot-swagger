package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtils;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productService.create(requestDtoMapper.mapToModel(productRequestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by Id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return responseDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by Id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by Id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productService.update(requestDtoMapper
                .mapToModel(productRequestDto), id);
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get products with prices between given range")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from, @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "10") @ApiParam(value = "default value"
                    + " is 10") Integer count,
            @RequestParam(defaultValue = "0") @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "id") @ApiParam(value = "default value"
                    + " is ID") String sortBy) {
        Sort sort = SortUtils.createSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        List<Product> products = productService.getAllByPriceBetween(from, to, pageRequest);
        return products.stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping()
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "10") @ApiParam(value = "default value"
                    + " is 10") Integer count,
            @RequestParam(defaultValue = "0") @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "id") @ApiParam(value = "default value"
                    + " is ID") String sortBy) {
        Sort sort = SortUtils.createSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        List<Product> products = productService.getAll(pageRequest);
        return products.stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
