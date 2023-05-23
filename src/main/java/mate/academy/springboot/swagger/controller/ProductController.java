package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.SortParser;
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
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "Save product to database")
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return productMapper.toDto(
                productService.save(
                        productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get list of all products")
    public List<ProductResponseDto>
            getAll(@RequestParam(defaultValue = "0") Integer page,
                   @RequestParam(defaultValue = "20")
                   @ApiParam(value = "default value 20") Integer size,
                   @RequestParam(defaultValue = "id")
                   @ApiParam(value = "sorted by id by default") String sortBy) {
        Sort sort = Sort.by(SortParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Get all products with price between two parameters")
    public List<ProductResponseDto>
            getAllByPriceBetween(@RequestParam BigDecimal from,
                                 @RequestParam BigDecimal to,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "20")
                                 @ApiParam(value = "default value 20") Integer size,
                                 @RequestParam(defaultValue = "id")
                                 @ApiParam(value = "sorted by id by default") String sortBy) {
        Sort sort = Sort.by(SortParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Completely delete a product")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product in the database")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }
}
