package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.MapperDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String PAGE_DEFAULT = "0";
    private static final String SIZE_DEFAULT = "20";
    private final ProductService productService;
    private final MapperDto productMapper;
    private final SortUtil sortUtil;

    @Autowired
    public ProductController(ProductService productService,
                             MapperDto productMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortUtil = sortUtil;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toDto(productService.create(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find product by `id`")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by `id`")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by `id`")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.create(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products list by field sort")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = SIZE_DEFAULT)
                     @ApiParam(value = "default value is `" + SIZE_DEFAULT + "`") Integer count,
                     @RequestParam(defaultValue = PAGE_DEFAULT)
                     @ApiParam(value = "default value is `" + PAGE_DEFAULT + "`") Integer page,
                     @RequestParam(defaultValue = "id")
                     @ApiParam(value = "default sort field is `id`") String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/between-price")
    @ApiOperation(value = "Get all products list where price is between two values "
            + "and ability to pagination and sorting.")
    public List<ProductResponseDto> getAllWherePriceBetween(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max,
            @RequestParam(defaultValue = SIZE_DEFAULT)
            @ApiParam(value = "default value is `" + SIZE_DEFAULT + "`") Integer count,
            @RequestParam(defaultValue = PAGE_DEFAULT)
            @ApiParam(value = "default value is `" + PAGE_DEFAULT + "`") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default sort field is `id`") String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllSortedWherePriceBetween(min, max, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
