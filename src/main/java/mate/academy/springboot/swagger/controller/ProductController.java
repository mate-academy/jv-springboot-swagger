package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortingParser;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SortingParser sortingParser;
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.create(productRequestDtoMapper.mapToModel(requestDto));
        return productResponseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by ID")
    public ProductResponseDto get(@PathVariable Long id) {
        return productResponseDtoMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update existing product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productRequestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productResponseDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by ID")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                                @ApiParam(value = "default value is `20`")
                                                Integer count,
                                            @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "default value is `0`")
                                                Integer page,
                                            @RequestParam (defaultValue = "id")
                                                @ApiParam(value = "default value is `id`")
                                                String sortBy) {
        return productService.findAll(PageRequest.of(page, count, sortingParser.parse(sortBy)))
                .stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products according to a price range")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam (defaultValue = "20")
                                                          @ApiParam(value = "default value is `20`")
                                                              Integer count,
                                                          @RequestParam (defaultValue = "0")
                                                          @ApiParam(value = "default value is `0`")
                                                              Integer page,
                                                          @RequestParam (defaultValue = "id")
                                                          @ApiParam(value = "default value is `id`")
                                                              String sortBy) {
        return productService.findAllByPriceBetween(from, to,
                        PageRequest.of(page, count, sortingParser.parse(sortBy)))
                .stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
