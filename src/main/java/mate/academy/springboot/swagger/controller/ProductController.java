package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.PageRequestService;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final PageRequestService pageRequestService;

    public ProductController(ProductService productService,
                             ProductMapper productMapper, PageRequestService pageRequestService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.pageRequestService = pageRequestService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a product by ID")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productMapper.toResponseDto(productService.getById(id));
    }

    @PostMapping("/create")
    @ApiOperation("Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toResponseDto(product);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    @ApiOperation("Update a product by ID")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation("Get products sorted with pagination")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestService.getPageRequest(page, count, sortBy);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation("Get products between from and to prices, sorted")
    public List<ProductResponseDto> getAllProductsBetweenPrice(@RequestParam BigDecimal from,
                                           @RequestParam BigDecimal to,
                                           @RequestParam (defaultValue = "20") Integer count,
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "id") String sortBy) {
        PageRequest pageRequest = pageRequestService.getPageRequest(count, page, sortBy);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
