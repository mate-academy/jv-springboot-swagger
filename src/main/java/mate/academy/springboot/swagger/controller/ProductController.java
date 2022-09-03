package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoRequestMapper;
import mate.academy.springboot.swagger.mapper.DtoResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
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
    private final DtoRequestMapper<ProductRequestDto, Product> requestMapper;
    private final DtoResponseMapper<Product, ProductResponseDto> responseMapper;

    @PostMapping
    @ApiOperation(value = "Create new Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = requestMapper.toModel(requestDto);
        return responseMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return responseMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = requestMapper.toModel(requestDto);
        product.setId(id);
        return responseMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all producs")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20")
                                       @ApiParam(value = "default value is 20") Integer count,
                                       @RequestParam (defaultValue = "0")
                                       @ApiParam(value = "default value is 0") Integer page,
                                       @RequestParam (defaultValue = "id")
                                       @ApiParam(value = "default value is id") String sortBy) {
        Sort sort = SortingService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(count, page, sort);
        return productService.getAll(pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get product by price")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                         @RequestParam BigDecimal to,
                                         @RequestParam (defaultValue = "20")
                                         @ApiParam(value = "default value is 20") Integer count,
                                         @RequestParam (defaultValue = "0")
                                         @ApiParam(value = "default value is 0") Integer page,
                                         @RequestParam (defaultValue = "id")
                                         @ApiParam(value = "default value is id") String sortBy) {
        Sort sort = SortingService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(count, page, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}
