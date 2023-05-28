package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
@Api(value = "Product Management System",
        description = "Operations pertaining to products in Product Management System")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    @PostMapping
    @ApiOperation(value = "Add a product")
    public ProductResponseDto create(@ApiParam(value =
            "Product object store in database table", required = true)
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.create(productMapper.toModel(productRequestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by its id")
    public ProductResponseDto findById(@ApiParam(value =
            "Product id from which product object will retrieve", required = true)
                                       @PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by its id")
    public void deleteById(@ApiParam(value =
            "Product id from which product object will delete from database table",
            required = true)
                           @PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product")
    public ProductResponseDto update(@ApiParam(value =
            "Product id to update product object", required = true)
                                     @PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.update(productMapper.toModel(productRequestDto));
        return productMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "View a list of all the products, results are pageable and sortable")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "price") String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/between-price")
    @ApiOperation(value =
            "View a list of products with price between certain range, "
                    + "results are pageable and sortable")
    public List<ProductResponseDto> findAllByPriceBetween(
            @ApiParam(value = "The starting range of price for the products")
            @RequestParam(defaultValue = "0") BigDecimal from,
            @ApiParam(value = "The ending range of price for the products")
            @RequestParam(defaultValue = "0") BigDecimal to,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "price") String sortBy) {
        List<Sort.Order> orders = sortUtil.getSort(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
