package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.mapper.impl.ProductRequestDtoMapperImpl;
import mate.academy.springboot.swagger.mapper.impl.ProductResponseDtoMapperImpl;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.parser.ProductUrlParser;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final ProductUrlParser productUrlParser;
    private final ProductResponseDtoMapperImpl productResponseDtoMapper;
    private final ProductRequestDtoMapperImpl productRequestDtoMapper;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(ProductRequestDto dto) {
        return productResponseDtoMapper.toDto(productService
                .save(productRequestDtoMapper.toModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productResponseDtoMapper.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    @ApiOperation(value = "update product")
    public ProductResponseDto update(ProductResponseDto dto) {
        return productResponseDtoMapper.toDto(productService
                .update(productResponseDtoMapper.toModel(dto)));
    }

    @GetMapping
    @ApiOperation(value = "get products lists as pages")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(productUrlParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productResponseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get products lists as pages where price lies between range")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam (defaultValue = "0") BigDecimal from,
            @RequestParam (defaultValue = "1000") BigDecimal to,
            @RequestParam (defaultValue = "20") Integer count,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(productUrlParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getProductsByPriceBetween(from, to, pageRequest).stream()
                .map(productResponseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
