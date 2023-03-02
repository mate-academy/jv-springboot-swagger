package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortParamParser;
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
    private final SortParamParser sortParamParser;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             SortParamParser sortParamParser,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.sortParamParser = sortParamParser;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation("Create new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.mapToDto(productService
                .save(productMapper.mapToModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);

        return productMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation("Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/byPrice")
    @ApiOperation("Get all products by price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size,
                                                  @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAllByPrice(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
