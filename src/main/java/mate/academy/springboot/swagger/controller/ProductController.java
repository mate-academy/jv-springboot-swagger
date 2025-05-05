package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
import mate.academy.springboot.swagger.util.ParserUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "Save product to database")
    public ProductResponseDto save(@RequestBody(description = "Add product",
                                            required = true,
                                            content = @Content(
                                                    schema = @Schema(implementation = Product.class)
                                            ))
                                    @Valid ProductRequestDto productRequestDto) {
        return productMapper.toDto(productService.save(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product from database by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product from database by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product in database by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody(description = "Update product",
                                             required = true,
                                             content = @Content(
                                                     schema = @Schema(
                                                             implementation = Product.class
                                                     )
                                             ))
                                     @Valid ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products from database by price range")
    public List<ProductResponseDto> getProductsByPriceBetween(
            @ApiParam(value = "Price from")
            @RequestParam(defaultValue = "0") BigDecimal from,
            @ApiParam(value = "Price to")
            @RequestParam BigDecimal to,
            @ApiParam(value = "Current page number (starts from 0)")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "Number of elements per page")
            @RequestParam(defaultValue = "20") Integer size,
            @ApiParam(value = "Sort by field")
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(ParserUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getProductsByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Get all products from database")
    public List<ProductResponseDto> getAll(
            @ApiParam(value = "Current page number (starts from 0)")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "Number of elements per page")
            @RequestParam(defaultValue = "20")
            Integer size,
            @ApiParam(value = "Sort by field")
            @RequestParam(defaultValue = "id")
            String sortBy) {
        Sort sort = Sort.by(ParserUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

}
