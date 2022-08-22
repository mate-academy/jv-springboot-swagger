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
import mate.academy.springboot.swagger.util.SortUtil;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all products as pages")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                                @ApiParam(value = "20 by default") Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "0 by default") Integer page,
                                            @RequestParam(defaultValue = "id")
                                                @ApiParam(value = "ID by default") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sortData(sortBy));
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
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
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all product as pages by price")
    public List<ProductResponseDto> findAllInRange(@RequestParam(defaultValue = "20")
                                                       @ApiParam(value = "20 by default")
                                                       Integer count,
                                                   @RequestParam(defaultValue = "0")
                                                   @ApiParam(value = "0 by default") Integer page,
                                                   @RequestParam(defaultValue = "0")
                                                       @ApiParam(value = "0 by default")
                                                       BigDecimal from,
                                                   @RequestParam(defaultValue = "Integer.MAX_VALUE")
                                                       @ApiParam(value = "Integer.MAX by default")
                                                       BigDecimal to,
                                                   @RequestParam(defaultValue = "id")
                                                   @ApiParam(value = "ID by default") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sortData(sortBy));
        return productService
                .findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
