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
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortUtil;
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
    private final RequestDtoMapper<ProductRequestDto, Product> requestMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseMapper;

    @PostMapping("/create")
    @ApiOperation("create a new Product")
    public ProductResponseDto create(@RequestBody(description = "product to add", required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
                                         @Valid ProductRequestDto requestDto) {
        return responseMapper.mapToDto(productService.save(requestMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation("get Product by ID")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseMapper.mapToDto(productService.get(id));
    }

    @GetMapping
    @ApiOperation("get all products with pagination and ability to sort by price "
            + "or by title in ASC or DESC order")
    public List<ProductResponseDto> findAll(
            @RequestParam @ApiParam(value = "the page index", defaultValue = "0") Integer page,
            @RequestParam @ApiParam(value = "the page size", defaultValue = "20") Integer count,
            @RequestParam @ApiParam(value = "sort by price", defaultValue = "price")
            String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation("get all products where price is between two values received "
            + "as a `RequestParam` inputs with pagination and ability to sort by price "
            + "or by title in ASC or DESC order")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam @ApiParam("price from") BigDecimal from,
            @RequestParam @ApiParam("price to") BigDecimal to,
            @RequestParam @ApiParam(value = "the page index", defaultValue = "0") Integer page,
            @RequestParam @ApiParam(value = "the page size", defaultValue = "20") Integer count,
            @RequestParam @ApiParam(value = "sort by price", defaultValue = "price")
            String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(from, to, pageRequest).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ApiOperation("update a Product")
    public ProductResponseDto update(@PathVariable Long id,
             @RequestBody(description = "product to add", required = true,
                     content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
                                     @Valid ProductRequestDto requestDto) {
        Product product = requestMapper.mapToModel(requestDto);
        product.setId(id);
        //
        return responseMapper.mapToDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete a Product by ID")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

}
