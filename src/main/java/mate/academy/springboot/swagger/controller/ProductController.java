package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
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
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortUtil = sortUtil;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.create(productMapper.toModel(requestDto));
        productService.create(product);
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product by id")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        return productMapper.toDto(productService
                .update(productMapper.toModel(requestDto), id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "get a list of all products")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20")
                                               @ApiParam(value = "default value is '20'")
                                               Integer count,
                                           @RequestParam (defaultValue = "0")
                                               @ApiParam(value = "default value is '0'")
                                           Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "default value is 'id'")
                                               String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sort(sortBy));
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get a list of the products with a specified price range")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                               @RequestParam BigDecimal to,
                                               @RequestParam (defaultValue = "20")
                                                      @ApiParam(value = "default value is '20'")
                                                      Integer count,
                                               @RequestParam (defaultValue = "0")
                                                      @ApiParam(value = "default value is '0'")
                                                      Integer page,
                                               @RequestParam (defaultValue = "id")
                                                      @ApiParam(value = "default value is 'id'")
                                                      String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sort(sortBy));
        return productService.getAllByPrice(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
