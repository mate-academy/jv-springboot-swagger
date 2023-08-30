package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortingService sortingService;

    @PostMapping
    @ApiOperation("Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.create(productMapper.mapToEntity(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("{id}")
    @ApiOperation("Get a product by `id`")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete a product by `id`")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping("{id}")
    @ApiOperation("Update a product by `id`")
    public ProductResponseDto updateById(@PathVariable Long id,
                                         @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToEntity(productRequestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.create(product));
    }

    @GetMapping
    @ApiOperation("Get product list")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "10")
                                           @ApiParam("default value is `10`") Integer count,
                                           @RequestParam(defaultValue = "4")
                                           @ApiParam("default value is `4`") Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam("default value is `id`") String sortBy) {
        PageRequest pageRequest = sortingService.getPageRequest(count, page, sortBy);
        return productService.getAll(pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/between")
    @ApiOperation("Get products with price between `from` and `to`")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                         @RequestParam BigDecimal to,
                         @RequestParam (defaultValue = "10")
                         @ApiParam("default value is `10`") Integer count,
                         @RequestParam(defaultValue = "4")
                         @ApiParam("default value is `4`") Integer page,
                         @RequestParam(defaultValue = "id")
                         @ApiParam("default value is `id`") String sortBy) {
        PageRequest pageRequest = sortingService.getPageRequest(count, page, sortBy);
        return productService.getAllPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
