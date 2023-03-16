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
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.Sorter;
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

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.mapToModel(requestDto));
        return productMapper.mapToDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        productService.save(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping({"/id"})
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Find all products between from and to prices")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                        @RequestParam BigDecimal to, @RequestParam (defaultValue = "20")
                        @ApiParam(value = "default value = '20") Integer count,
                        @RequestParam (defaultValue = "0") Integer page,
                        @RequestParam (defaultValue = "id")
                        @ApiParam (defaultValue = "default value = 'id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count,
                Sort.by(Sorter.getAllSortOrders(sortBy)));
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping()
    @ApiOperation(value = "Find all products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                         @ApiParam(value = "default value = '20")Integer count,
                         @RequestParam (defaultValue = "0") Integer page,
                         @RequestParam (defaultValue = "id")
                         @ApiParam (defaultValue = "default value = 'id")String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count,
                Sort.by(Sorter.getAllSortOrders(sortBy)));
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

