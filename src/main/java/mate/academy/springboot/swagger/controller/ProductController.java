package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.entity.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.util.SortUtil;
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
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    @ApiOperation(value = "Add a new product")
    @PostMapping
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toEntity(requestDto);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "Delete product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ApiOperation(value = "Update product")
    @PutMapping("/{id}")
    public ProductResponseDto update(
            @RequestBody ProductRequestDto requestDto,
            @PathVariable Long id
    ) {
        Product product = productMapper.toEntity(requestDto);
        product.setId(id);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "Find all products with possibility sorting and ordering")
    @GetMapping
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20")
                @ApiParam(value = "default value is 20") Integer count,
            @RequestParam(defaultValue = "0")
                @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "id")
                @ApiParam(value = "format PARAMETER:ORDER, default order is ascending")
                String sortBy

    ) {
        Sort sort = sortUtil.parseSorting(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find all products filtered by price")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20")
                @ApiParam(value = "default value is 20") Integer count,
            @RequestParam(defaultValue = "0")
                @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "id")
                @ApiParam(value = "format PARAMETER:ORDER, default order is ascending")
                String sortBy
    ) {
        Sort sort = sortUtil.parseSorting(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        List<Product> products = productService.findAllByPriceBetween(from, to, pageRequest);
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
