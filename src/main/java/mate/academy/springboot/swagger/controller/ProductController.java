package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
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
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by Id")
    public ProductResponseDto getById(@RequestParam Long id) {
        return productMapper.toResponseDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by Id")
    public void deleteById(@RequestParam Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    @ApiOperation(value = "Update product by Id")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @RequestParam Long id) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam Integer count,
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.parse(sortBy));
        return productService.getAll(pageRequest)
               .stream()
               .map(productMapper::toResponseDto)
               .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get all products by price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam Integer count,
                                                         @RequestParam (defaultValue = "0")
                                                              Integer page,
                                                         @RequestParam (defaultValue = "id")
                                                              String sortBy) {
        Pageable pageable = PageRequest.of(page, count, sortUtil.parse(sortBy));
        return productService.findAllByPriceBetween(from, to, pageable)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
