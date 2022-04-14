package mate.academy.springboot.swagger.controller;

import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.pagination.Pagination;
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
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final Pagination pagination;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return productMapper.toDto(productService.save(productMapper.toModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @GetMapping
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "10") Integer size,
                                           @RequestParam (defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pagination.paginate(page, size, sortBy);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productMapper.toModel(dto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get products with price between")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "10") Integer size,
            @RequestParam (defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pagination.paginate(page, size, sortBy);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
