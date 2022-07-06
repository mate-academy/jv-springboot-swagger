package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.PageRequestService;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProductService productService;
    private ProductMapper productMapper;
    private PageRequestService pageRequestService;

    @Autowired
    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             PageRequestService pageRequestService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.pageRequestService = pageRequestService;
    }

    @PostMapping
    @ApiOperation(value = "create new product")
    public ProductResponseDto create(ProductRequestDto requestDto) {
        return productMapper.toDto(productService.create(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by ID")
    public ProductResponseDto getById(@PathVariable(name = "id") Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "delete product by ID")
    public void delete(@PathVariable(name = "id") Long id) {
        productService.delete(id);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "update product by ID")
    public ProductResponseDto update(@PathVariable(name = "id") Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product updatedProduct = productMapper.toModel(requestDto);
        updatedProduct.setId(id);
        return productMapper.toDto(productService.update(updatedProduct));
    }

    @GetMapping
    @ApiOperation(value = "get all products (5 unit per page by default)")
    public List<ProductResponseDto> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5")
            @ApiParam(value = "default value is 5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return productService.getAll(pageRequestService.getPageRequest(page, size, sortBy))
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products by price diapason (5 unit per page by default)")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam(name = "from") BigDecimal from,
            @RequestParam(name = "to") BigDecimal to,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5")
            @ApiParam(value = "default value is 5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return productService.getAllByPriceBetween(from, to,
                        pageRequestService.getPageRequest(page, size, sortBy)).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
