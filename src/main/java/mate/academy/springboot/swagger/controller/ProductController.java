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
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.OrderParser;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final OrderParser orderParser;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(
            @RequestBody(description = "product to create", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
            ProductRequestDto requestDto
    ) {
        Product product = productService.save(productMapper.toModel(requestDto));
        return productMapper.toRespDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "find product by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productMapper.toRespDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public boolean deleteById(@PathVariable Long id) {
        return productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto updateById(
            @PathVariable Long id,
            @RequestBody(description = "product to create", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
            ProductRequestDto requestDto
    ) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.toRespDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "find sorted list of products as a page")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "0")
                @ApiParam (value = "page number") Integer page,
            @RequestParam (defaultValue = "10")
                @ApiParam (value = "page size") Integer size,
            @RequestParam (defaultValue = "id")
                @ApiParam (value = "column name to sort by, default is id:ASC") String sortBy
    ) {
        List<Sort.Order> orders = orderParser.parseOrdersByRequest(sortBy);
        return productService.findAll(PageRequest.of(page, size, Sort.by(orders))).stream()
                .map(productMapper::toRespDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price-between")
    @ApiOperation(value = "find sorted list of products by price in range as a page")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam
                @ApiParam (value = "minimum price") BigDecimal priceFrom,
            @RequestParam
                @ApiParam (value = "maximum price") BigDecimal priceTo,
            @RequestParam (defaultValue = "0")
                @ApiParam (value = "page number") Integer page,
            @RequestParam (defaultValue = "10")
                @ApiParam (value = "page size") Integer size,
            @RequestParam (defaultValue = "id")
                @ApiParam (value = "column name to sort by, default is id:ASC") String sortBy
    ) {
        List<Sort.Order> orders = orderParser.parseOrdersByRequest(sortBy);
        return productService.findAllByPriceBetween(priceFrom, priceTo,
                        PageRequest.of(page, size, Sort.by(orders))).stream()
                .map(productMapper::toRespDto)
                .collect(Collectors.toList());
    }
}
