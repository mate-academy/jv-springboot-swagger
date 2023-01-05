package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SortParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto save(
            @RequestBody
            @ApiParam(value = "Insert price and title") ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.toModel(productRequestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "get Product by ID")
    public ProductResponseDto get(
            @PathVariable
            @ApiParam(value = "Insert ID") Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "delete Product by ID")
    public void delete(
            @PathVariable
            @ApiParam(value = "Insert ID") Long id) {
        productService.delete(id);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "update Product")
    public ProductResponseDto update(
            @PathVariable
            @ApiParam(value = "Insert ID") Long id,
            @RequestBody
            @ApiParam(value = "Insert ID, price, title") ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "get all products with pagination and ability to "
            + "sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAllAndSortByPrice(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "Insert items per page, default: 20") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Insert page number, default: 0") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "Insert sorted fields and directions, default: id:DESC")
                    String sortBy) {
        Sort sort = Sort.by(SortParser.sortBy(sortBy));
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.findAll(pageable)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetweenAndSort(
            @RequestParam
            @ApiParam(value = "Insert start price") Integer from,
            @RequestParam
            @ApiParam(value = "Insert end price") Integer to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "Insert items per page, default: 20") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Insert page number, default: 0") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "Insert sorted fields and directions, default: id:DESC")
                    String sortBy) {
        Sort sort = Sort.by(SortParser.sortBy(sortBy));
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.findProductsByPriceBetween(from, to, pageable)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    public void inject() {
        for (int i = 0; i < 1000; i++) {
            Product product = new Product();
            product.setTitle("Title" + i);
            product.setPrice(i + 100);
            productService.save(product);
        }
    }
}
