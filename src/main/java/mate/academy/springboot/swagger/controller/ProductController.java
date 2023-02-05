package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortParser;
import mate.academy.springboot.swagger.service.map.ProductMapper;
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
    private final SortParser sortParser;

    public ProductController(ProductService productService,
                             ProductMapper productMapper, SortParser sortParser) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortParser = sortParser;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toDto(productService.save(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get the product by its ID")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productMapper.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the product by it ID")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "Product with id " + id + " was deleted.";
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update the product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products with pagination and ability " +
            "to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer count,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-range")
    @ApiOperation(value = "Get all products where price is between two values and ability " +
            "to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam Long from,
                                                          @RequestParam Long to,
                                                          @RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer count,
                                                          @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
