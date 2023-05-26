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
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.Mapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final Mapper<Product, ProductRequestDto, ProductResponseDto> productMapper;
    private final ProductService productService;
    private final SortUtil sortUtil;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto add(@RequestBody(description = "Add parameters to create product",
            required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
                                      ProductRequestDto requestDto) {
        return productMapper.mapToDto(productService
                .save(productMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody(description = "Update parameters to product",
                                             required = true,
                                             content = @Content(schema =
                                             @Schema(implementation = ProductRequestDto.class)))
                                     ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> findAll(@ApiParam(value = "count of elements on one page",
                                                defaultValue = "20")
                                                @RequestParam (defaultValue = "20")
                                                Integer count,
                                                @ApiParam(value = "page number to display",
                                                        defaultValue = "0")
                                                @RequestParam (defaultValue = "0")
                                                Integer page,
                                                @ApiParam(value = "parameter to sort by",
                                                    defaultValue = "id")
                                                @RequestParam (defaultValue = "id")
                                                String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products between price")
    public List<ProductResponseDto> findByPriceBetween(@ApiParam(value = "minimum price")
                                                        @RequestParam BigDecimal from,
                                                       @ApiParam(value = "maximum price")
                                                       @RequestParam BigDecimal to,
                                                       @ApiParam(value =
                                                               "count of elements on one page",
                                                               defaultValue = "20")
                                                       @RequestParam (defaultValue = "20")
                                                           Integer count,
                                                       @ApiParam(value = "page number to display",
                                                               defaultValue = "0")
                                                       @RequestParam (defaultValue = "0")
                                                           Integer page,
                                                       @ApiParam(value = "parameter to sort by",
                                                               defaultValue = "id")
                                                       @RequestParam (defaultValue = "id")
                                                           String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.findByPriceBetween(from, to, pageable).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
