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
import mate.academy.springboot.swagger.util.SortParser;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortParser sortParser;

    @GetMapping
    @ApiOperation(value = "Get all products and sort them")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "5")
            @ApiParam(value = "Default value 5") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "Default value 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "Default value of sorting: by id") String sortBy) {

        Sort sort = Sort.by(sortParser.getSortParams(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation(value = "Add product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.toModel(productRequestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get selected product")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete selected product")
    public ProductResponseDto delete(@PathVariable Long id) {
        ProductResponseDto deletedProduct = productMapper.toDto(productService.getById(id));
        productService.delete(productService.getById(id));
        return deletedProduct;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update selected product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products by price and sort them")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam (defaultValue = "5")
            @ApiParam (value = "Default value 5") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam (value = "Default value 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam (value = "Default value of sorting: by id") String sortBy,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Lower border, default value 0") BigDecimal from,
            @RequestParam (defaultValue = "" + Integer.MAX_VALUE)
            @ApiParam(value = "Upper border, default value " + Integer.MAX_VALUE) BigDecimal to) {
        Sort sort = Sort.by(sortParser.getSortParams(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
