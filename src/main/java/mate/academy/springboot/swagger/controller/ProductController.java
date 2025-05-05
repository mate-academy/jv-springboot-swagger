package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.Parser;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final Parser parser;

    @PostMapping
    @ApiOperation(value = "Save product")
    public ProductResponseDto save(@RequestBody(description = "Product to add", required = true,
            content = @Content(
                    schema = @Schema(implementation = Product.class)))
                                    ProductRequestDto productRequestDto) {
        return productMapper.toDto(
                productService.save(
                        productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @GetMapping
    @ApiOperation(value = "Get list of all products")
    public List<ProductResponseDto> getAll(@ApiParam(value = "current page, default value 0")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "page size, default value 20")
            @RequestParam(defaultValue = "20") Integer size,
            @ApiParam(value = "sorting method,sorted by id by default")
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(parser.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("price-between")
    @ApiOperation(value = "Get all products with price between two parameters")
    public List<ProductResponseDto>
            getAllByPriceBetween(
            @ApiParam(value = "from price", required = true)
            @RequestParam BigDecimal from,
            @ApiParam(value = "to price", required = true)
            @RequestParam BigDecimal to,
            @ApiParam(value = "current page, default value 0")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "default value 20")
            @RequestParam(defaultValue = "20")Integer size,
            @ApiParam(value = "sorted by id by default")
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(parser.getOrders(sortBy));
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
                                     @RequestBody(description = "Product ot update",
                                             required = true,
                                             content = @Content(
                                                     schema =
                                                     @Schema(implementation = Product.class)))
                                     ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }
}
