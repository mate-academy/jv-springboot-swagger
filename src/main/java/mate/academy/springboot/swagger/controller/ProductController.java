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
import mate.academy.springboot.swagger.service.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.ProductSorter;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final ProductSorter productSorter;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto product) {
        return productMapper.toDto(productService.create(productMapper.toModel(product)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get the product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update the product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "5")
            @ApiParam(value = "default value is 5") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is id") String sortBy) {
        Sort sort = productSorter.sortProducts(sortBy);
        return productService.findAll(PageRequest.of(page, count, sort))
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products within the price range")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "5")
            @ApiParam(value = "default value is 5") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is id") String sortBy) {
        Sort sort = productSorter.sortProducts(sortBy);
        return productService.findAllByPriceBetween(from, to, PageRequest.of(page, count, sort))
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
