package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final SortingService sortingService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find product by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestParam ProductRequestDto requestDto) {
        Product model = productMapper.toModel(requestDto);
        model.setId(id);
        return productMapper.toDto(productService.save(model));
    }

    @GetMapping
    @ApiOperation(value = "Find all products, display the result when using pagination")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                            @ApiParam(value = "default value is 20")
                                            Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is 0")
                                            Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "default value is id")
                                            String sortBy) {
        List<Sort.Order> orders = sortingService.getSortingOrder(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value =
            "Find all products between two price, display the result when using pagination")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                            @ApiParam(value = "default value is 20")
                                            Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is 0")
                                            Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "default value is id")
                                            String sortBy,
                                            @RequestParam BigDecimal priceFrom,
                                            @RequestParam BigDecimal priceTo) {
        List<Sort.Order> orders = sortingService.getSortingOrder(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(priceFrom, priceTo, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
