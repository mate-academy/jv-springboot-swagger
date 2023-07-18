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
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.util.SortRequestParser;
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
    private ProductService productService;
    private RequestDtoMapper<ProductRequestDto, Product> requestMapper;
    private ResponseDtoMapper<Product, ProductResponseDto> responseMapper;

    @PostMapping("/create")
    @ApiOperation("Endpoint to create new products")
    public ProductResponseDto create(@RequestBody(description = "Product to add.", required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
                                         @Valid ProductRequestDto productRequestDto) {
        return responseMapper.mapToDto(productService.save(
                requestMapper.mapToModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation("Endpoint to get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return responseMapper.mapToDto(productService.get(id));
    }

    @GetMapping
    @ApiOperation("Endpoint to get all products with pagination and ability "
            + "to sort by price or by title in ASC or DESC order")
    public List<ProductResponseDto> findAll(@ApiParam(value = "page size", defaultValue = "20")
                                            @RequestParam(defaultValue = "20")
                                            Integer count,
                                            @ApiParam(value = "page number", defaultValue = "0")
                                            @RequestParam(defaultValue = "0")
                                            Integer page,
                                            @ApiParam(value = "sort param", defaultValue = "price")
                                            @RequestParam(defaultValue = "price")
                                            String sortBy) {
        Sort sort = Sort.by(SortRequestParser.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAll(request).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation("Endpoint to get all products where price is between two values received as a "
            + "RequestParam inputs, also with pagination and ability to sort by price or by title "
            + "in ASC or DESC order")
    public List<ProductResponseDto> findAllByPriceBetween(@ApiParam(value = "price from value",
            defaultValue = "0")
                                                          @RequestParam(defaultValue = "0")
                                                          BigDecimal from,
                                                          @ApiParam(value = "price to value",
            defaultValue = "1000")
                                                          @RequestParam(defaultValue = "1000")
                                                          BigDecimal to,
                                                          @ApiParam(value = "page size",
            defaultValue = "20")
                                                          @RequestParam(defaultValue = "20")
                                                          Integer count,
                                                          @ApiParam(value = "page number",
            defaultValue = "0")
                                                          @RequestParam(defaultValue = "0")
                                                          Integer page,
                                                          @ApiParam(value = "sort param",
            defaultValue = "id")
                                                          @RequestParam(defaultValue = "id")
                                                          String sortBy) {
        Sort sort = Sort.by(SortRequestParser.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAllByPrice(from, to, request).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    @ApiOperation("Endpoint to update product with Id")
    public ProductResponseDto update(@RequestParam Long id,
                                     @RequestBody(description = "Product to update.",
                                             required = true, content = @Content(schema =
                                     @Schema(implementation = ProductRequestDto.class)))
                                     @Valid ProductRequestDto productRequestDto) {
        Product product = requestMapper.mapToModel(productRequestDto);
        product.setId(id);
        return responseMapper.mapToDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Endpoint to delete product by Id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
