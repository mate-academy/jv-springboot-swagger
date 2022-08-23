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
import mate.academy.springboot.swagger.util.ParserUtil;
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
    private final ProductService productService;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestMapper;
    private final ParserUtil parserUtil;
    
    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productRequestMapper.toModel(requestDto));
        return productResponseMapper.toDto(product);
    }
    
    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productResponseMapper.toDto(productService.getById(id));
    }
    
    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list by price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal fromPrice,
            @RequestParam BigDecimal toPrice,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value id '20'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'") String sortBy) {
        Sort sort = Sort.by(parserUtil.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(fromPrice, toPrice, pageRequest)
                .stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @GetMapping
    @ApiOperation(value = "Get product list")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value id '20'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'") String sortBy) {
        Sort sort = Sort.by(parserUtil.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productRequestMapper.toModel(requestDto);
        product.setId(id);
        return productResponseMapper.toDto(productService.save(product));
    }
    
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
