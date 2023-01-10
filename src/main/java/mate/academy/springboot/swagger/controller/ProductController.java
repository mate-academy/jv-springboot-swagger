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
import mate.academy.springboot.swagger.util.PageRequestParser;
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
    private final PageRequestParser pageRequestParser;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             PageRequestParser pageRequestParser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.pageRequestParser = pageRequestParser;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product savedProduct = productService.save(productMapper.toModel(dto));
        return productMapper.toDto(savedProduct);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update a product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productMapper.toModel(dto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "get a list of sorted products (ASC order by default "
            + "with pagination and filtering by price options. "
            + "To sort by field in ASC or DESC order, type 'fieldName':'order'.")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "0")
                                            @ApiParam(value = "default value is 0") Integer page,
                                            @RequestParam (defaultValue = "20")
                                            @ApiParam(value = "default value is 20") Integer size,
                                            @RequestParam (defaultValue = "id")
                                            @ApiParam(value = "default value is id") String sortBy,
                                            @RequestParam (defaultValue = "0")
                                            @ApiParam(value = "default value is 0") BigDecimal from,
                                            @RequestParam (defaultValue = "1000000")
                                            @ApiParam(value = "default value is 1000000")
                                            BigDecimal to) {
        PageRequest pageRequest = pageRequestParser.parse(page, size, sortBy);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    @ApiOperation(value = "test method, add a list of mock products")
    public List<ProductResponseDto> injectMockData() {
        for (int i = 1; i <= 50; i++) {
            Product product = new Product();
            product.setTitle("Product " + i);
            product.setPrice(BigDecimal.valueOf(i * 250));
            productService.save(product);
        }
        return productService.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
