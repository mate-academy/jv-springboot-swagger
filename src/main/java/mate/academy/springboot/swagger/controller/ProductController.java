package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.impl.ProductMapper;
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
    private final SortUtil sortUtil;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortUtil = sortUtil;
    }

    @PostMapping
    @ApiOperation("Save a new product to the database")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.save(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id from path variable")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @GetMapping
    @ApiOperation("Get all products where price is between specified bounds, "
            + "with pagination and sorting")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam(defaultValue = "0", required = false)
            @ApiParam("Default value is 0") BigDecimal priceFrom,
            @RequestParam(defaultValue = "1000000000000000", required = false)
            @ApiParam("Default value is 1000000000000000") BigDecimal priceTo,
            @RequestParam(defaultValue = "20") @ApiParam("Default value is 20") Integer size,
            @RequestParam(defaultValue = "0") @ApiParam("Default value is 0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return productService.findAllByPriceBetween(priceFrom, priceTo, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product by id from the path variable")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
