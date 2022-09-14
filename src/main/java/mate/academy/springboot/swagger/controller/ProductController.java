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
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SortingParser;
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
    private final SortingParser sortingParser;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             SortingParser sortingParser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortingParser = sortingParser;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.add(productMapper.mapToModel(requestDto));
        productService.add(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public void update(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        productService.update(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products within price range")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam (defaultValue = "20")
                                                              @ApiParam(value
                                                                      = "default value is '20'")
                                                              Integer count,
                                                          @RequestParam (defaultValue = "0")
                                                              @ApiParam(value
                                                                      = "default value is '0'")
                                                              Integer page,
                                                          @RequestParam (defaultValue = "id")
                                                              @ApiParam(value
                                                                      = "default value is 'id'")
                                                              String sortBy) {
        return productService.findAllByPriceBetween(from, to, PageRequest.of(page, count,
                        sortingParser.parse(sortBy)))
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                                @ApiParam(value = "default value is '20'")
                                                Integer count,
                                            @RequestParam (defaultValue = "0")
                                            @ApiParam(value
                                                    = "default value is '0'")
                                            Integer page,
                                            @RequestParam (defaultValue = "id")
                                                @ApiParam(value
                                                        = "default value is 'id'") String sortBy) {
        return productService.findAll(PageRequest.of(page, count, sortingParser.parse(sortBy)))
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
