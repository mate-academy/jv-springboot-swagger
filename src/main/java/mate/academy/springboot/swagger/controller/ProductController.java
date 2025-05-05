package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.service.sort.SortParse;
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
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ResponseDtoMapper<Product, ProductResponseDto> responseDtoMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ProductService productService;

    @PostMapping("/create")
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        return responseDtoMapper.toDto(productService
                .save(requestDtoMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get the product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.toDto(productService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "10") @ApiParam(value = "10 by default") Integer count,
            @RequestParam (defaultValue = "1") @ApiParam(value = "1 by default") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value = "id by default") String sortBy) {
        PageRequest pageRequest = PageRequest.of((page - 1), count, SortParse.parse(sortBy));
        return productService.getAll(pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update the product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = requestDtoMapper.toModel(productRequestDto);
        product.setId(id);
        return responseDtoMapper.toDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products filtered by price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "10") @ApiParam(value = "10 by default") Integer count,
            @RequestParam (defaultValue = "1") @ApiParam(value = "1 by default") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value = "id by default") String sortBy) {
        PageRequest pageRequest = PageRequest.of((page - 1), count, SortParse.parse(sortBy));
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
