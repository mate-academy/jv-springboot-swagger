package mate.academy.springboot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.dto.ProductRequestDto;
import mate.academy.springboot.dto.ProductResponseDto;
import mate.academy.springboot.mapper.ProductMapper;
import mate.academy.springboot.model.Product;
import mate.academy.springboot.service.ProductService;
import mate.academy.springboot.service.ProductSorter;
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
    private final ProductSorter productSorter;

    @ApiOperation(value = "Add a new product")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product model = productMapper.toModel(requestDto);
        Product product = productService.save(model);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @ApiOperation(value = "Delete product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
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
                                           @RequestParam (defaultValue = "10")
                                           @ApiParam(value = "default value is 10") Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "default value is 0") Integer page,
                                           @RequestParam (defaultValue = "price")
                                           @ApiParam(value = "default value is price")
                                           String sortBy) {
        PageRequest pageRequest = PageRequest.of(page,count,productSorter.sortByParam(sortBy));
        List<Product> allProductsByPrice = productService.findAllByPriceBetween(from, to,
                pageRequest);
        return allProductsByPrice
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Find all products with sorting and ordering")
    @GetMapping("/all")
    List<ProductResponseDto> getAll(@RequestParam (defaultValue = "10")
                                    @ApiParam(value = "default value is 10") Integer count,
                                    @RequestParam (defaultValue = "0")
                                    @ApiParam(value = "default value is 0") Integer page,
                                    @RequestParam (defaultValue = "price")
                                    @ApiParam(value = "default value is price")
                                    String sortBy) {
        PageRequest pageRequest = PageRequest.of(page,count,productSorter.sortByParam(sortBy));
        List<Product> allProducts = productService.findAll(pageRequest);
        return allProducts
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
