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
import mate.academy.springboot.swagger.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final Parser parser;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody(description = "Product to add",
            required = true, content = @Content(
                    schema = @Schema(implementation = ProductRequestDto.class)))
                                         @Valid ProductRequestDto product) {
        return responseDtoMapper.mapToDto(productService
                .save(requestDtoMapper.mapToModel(product)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id from DB")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id from DB")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id from DB")
    public ProductResponseDto update(@RequestBody
            (description = "Update product ", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
                                         @Valid ProductRequestDto productRequestDto,
                                     @PathVariable Long id) {
        Product product = requestDtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        Product updatedProduct = productService.update(product);
        return responseDtoMapper.mapToDto(updatedProduct);
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                                @ApiParam(value = "default value is `20`")
                                                Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is `0`") Integer page,
                                            @RequestParam(defaultValue = "id")
                                                @ApiParam(value = "default value is id")
                                                String sortBy) {
        List<Sort.Order> orders = parser.parseSortByString(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list between price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "The minimum price") BigDecimal from,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "The maximum price") BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default value is id") String sortBy
    ) {
        List<Sort.Order> orders = parser.parseSortByString(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
