package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.MapperDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortOrderUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MapperDto<ProductRequestDto,
            ProductResponseDto,
            Product> productMapper;
    private final SortOrderUtil sortOrderUtil;

    @PostMapping
    @ApiOperation(value = "Add new product")
    public ProductResponseDto add(
            @RequestBody(description = "Product for update ",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
            ProductRequestDto productRequestDto) {
        return productMapper.toDto(productService.add(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(
            @PathVariable Long id,
            @RequestBody(description = "Product for update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
            ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Find all products with sorting and pagination")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "Products limit on page, default value 10.")
            Integer size,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Number of page, default value 0.")
            Integer page,
            @RequestParam(defaultValue = "id:DESC")
            @ApiParam(value = "Name field to sort and direction, default value id:DESC")
            String sortBy) {
        Sort sort = Sort.by(sortOrderUtil.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between")
    @ApiOperation(value = "Find all products price between with sorting and pagination")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam
            @ApiParam(value = "Range price - start")
            BigDecimal from,
            @RequestParam
            @ApiParam(value = "Range price - finish")
            BigDecimal to,
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "Products limit on page, default value 10.")
            Integer size,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Number of page, default value 0.")
            Integer page,
            @RequestParam(defaultValue = "price:ASC")
            @ApiParam(value = "Name field to sort and direction, default value price:ASC")
            String sortBy) {
        Sort sort = Sort.by(sortOrderUtil.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
