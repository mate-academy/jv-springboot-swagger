package mate.academy.springboot.swagger.controller;

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
import mate.academy.springboot.swagger.service.SortMaker;
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
@AllArgsConstructor
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final SortMaker sortMaker;

    @ApiOperation(value = "Add a new product")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product model = productMapper.toModel(requestDto);
        Product product = productService.create(model);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @ApiOperation(value = "Delete product by id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "Update product")
    @PutMapping("/{id}")
    ProductResponseDto update(@PathVariable Long id,
                              @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.update(product));
    }

    @ApiOperation(value = "Find all products by between price with sorting and ordering")
    @GetMapping("/all-price")
    List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                           @RequestParam BigDecimal to,
                                           @RequestParam (defaultValue = "3")
                                           @ApiParam(value = "default value is 3") Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "default value is 0") Integer page,
                                           @RequestParam (defaultValue = "id")
                                           @ApiParam(value = "format PARAMETER:ORDER,"
                                                   + " default is DESC")
                                           String sortBy) {
        PageRequest pageRequest = PageRequest.of(page,count,sortMaker.make(sortBy));
        List<Product> allProductsByPrice = productService.getAllByPrice(from, to, pageRequest);
        return allProductsByPrice
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find all products with sorting and ordering")
    @GetMapping("/all")
    List<ProductResponseDto> getAll(@RequestParam (defaultValue = "3")
                                    @ApiParam(value = "default value is 3") Integer count,
                                    @RequestParam (defaultValue = "0")
                                    @ApiParam(value = "default value is 0") Integer page,
                                    @RequestParam (defaultValue = "id")
                                    @ApiParam(value = "format PARAMETER:ORDER, default is DESC")
                                    String sortBy) {
        PageRequest pageRequest = PageRequest.of(page,count,sortMaker.make(sortBy));
        List<Product> allProducts = productService.getAll(pageRequest);
        return allProducts
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
