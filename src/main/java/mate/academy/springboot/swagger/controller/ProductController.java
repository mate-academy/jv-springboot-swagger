package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SorterUtil;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private SorterUtil sorterUtil;

    @ApiOperation(value = "Add a new product")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "Get a product by id")
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "Delete a product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "Update a product by id")
    @PutMapping("/{id}")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @ApiOperation(value = "Get all products with pagination "
            + "and sorting options (ASC & DESC) for title or price")
    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "10")
                                           @ApiParam(value = "default value is '10'")
                                           Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "default value is '0'")
                                           Integer page,
                                           @RequestParam(defaultValue = "title")
                                           @ApiParam(value = "default value is 'title'")
                                           String sortBy) {
        Sort sort = sorterUtil.getSorter(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllProducts(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all products having price in range from "
            + "@param 'from' to @param 'to' inclusively "
            + "with pagination and sorting options (ASC & DESC) for title or price")
    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam(defaultValue = "10")
                                                  @ApiParam(value = "default value is '10'")
                                                  Integer count,
                                                  @RequestParam(defaultValue = "0")
                                                  @ApiParam(value = "default value is '0'")
                                                  Integer page,
                                                  @RequestParam(defaultValue = "title")
                                                  @ApiParam(value = "default value is 'title'")
                                                  String sortBy) {
        Sort sort = sorterUtil.getSorter(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getProductsByRange(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
