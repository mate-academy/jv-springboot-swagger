package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
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
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toResponseDto(
                productService.create(
                        productMapper.toProductModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto getById(@PathVariable Long id,
                                      @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toProductModel(requestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.create(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "Default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "Default number of page") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = SortUtil.sort(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list between price values")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "Default value is '20'") Integer count,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = SortUtil.sort(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
