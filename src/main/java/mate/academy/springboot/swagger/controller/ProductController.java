package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    private final ProductService productService;
    private final SortService sortService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             SortService sortService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.sortService = sortService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.mapToModel(requestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product")
    public ProductResponseDto update(@PathVariable Long id,
                                      @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping
    @ApiOperation(value = "get list of products")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20")
                                               @ApiParam(value = "default value is `20`")
                                               Integer count,
                                           @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "default value is `0`")
                                           Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "default value is `id`")
                                               String sortBy) {
        Sort sort = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get list of products by price")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam (defaultValue = "20")
                                                             @ApiParam
                                                                     (value = "default "
                                                                             + "value is `20`")
                                                             Integer count,
                                                         @RequestParam (defaultValue = "0")
                                                             @ApiParam
                                                                     (value =
                                                                             "default value is `0`")
                                                             Integer page,
                                                         @RequestParam (defaultValue = "id")
                                                             @ApiParam
                                                                     (value = "default "
                                                                             + "value is `id`")
                                                             String sortBy) {
        Sort sort = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
