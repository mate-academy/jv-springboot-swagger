package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
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
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductController(ProductMapper productMapper,
                             ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.toModel(productRequestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                      @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "get products list")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "default value is id") String sortBy) {
        Sort sort = Sort.by(SortUtil.parseSortingOptions(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between")
    @ApiOperation(value = "get products list with price between")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from, @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "default value is id") String sortBy) {
        Sort sort = Sort.by(SortUtil.parseSortingOptions(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
