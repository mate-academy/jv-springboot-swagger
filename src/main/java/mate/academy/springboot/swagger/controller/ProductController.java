package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortingService sortingService;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             SortingService sortingService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortingService = sortingService;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto createNewProduct(@RequestBody ProductRequestDto dto) {
        return productMapper.mapToDto(productService.add(productMapper.mapToProduct(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToProduct(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.add(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(productService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                           @ApiParam(value = "Default value "
                                                   + "is `20`")
                                                   Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "Default value "
                                                   + "is `0`")
                                                   Integer page,
                                           @RequestParam (defaultValue = "id")
                                           @ApiParam(value = "Default value "
                                                   + "is `id`")
                                                   String sortBy) {
        Sort sort = Sort.by(sortingService.parseSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all product where price between input params")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam(defaultValue = "20")
                                                         @ApiParam(value = "Default value "
                                                                 + "is `20`")
                                                                 Integer count,
                                                         @RequestParam (defaultValue = "0")
                                                         @ApiParam(value = "Default value "
                                                                 + "is `0`")
                                                                 Integer page,
                                                         @RequestParam (defaultValue = "id")
                                                         @ApiParam(value = "Default value "
                                                                 + "is `id`")
                                                                 String sortBy) {
        Sort sort = Sort.by(sortingService.parseSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
