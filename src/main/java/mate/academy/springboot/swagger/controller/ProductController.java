package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseMapper;
import mate.academy.springboot.swagger.util.RequestParamParser;
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

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final RequestMapper<ProductRequestDto, Product> requestMapper;
    private final ResponseMapper<ProductResponseDto, Product> responseMapper;

    @PostMapping
    @ApiOperation(value = "Create products in database")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return responseMapper
                .mapToDto(productService
                        .save(requestMapper
                                .mapToModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product from database by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return responseMapper
                .mapToDto(productService
                        .findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product from database by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product in database by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = requestMapper.mapToModel(productRequestDto);
        product.setId(id);
        return responseMapper
                .mapToDto(productService
                        .update(product));
    }

    @GetMapping("/find-all")
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "10")
                                                @ApiParam(value = "default value is 10")
                                                Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "default value is 0")
                                            Integer page,
                                            @RequestParam(defaultValue = "id")
                                               @ApiParam(value = "default value is id")
                                                String sortBy) {
        Sort sort = Sort.by(RequestParamParser.toSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all product between price")
    public List<ProductResponseDto> findAllByPrice(@RequestParam BigDecimal from,
                                                   @RequestParam BigDecimal to,
                                                   @RequestParam(defaultValue = "10")
                                                       @ApiParam(value = "default value is 10")
                                                       Integer count,
                                                   @RequestParam(defaultValue = "0")
                                                       @ApiParam(value = "default value is 0")
                                                       Integer page,
                                                   @RequestParam(defaultValue = "id")
                                                       @ApiParam(value = "default value is id")
                                                       String sortBy) {
        Sort sort = Sort.by(RequestParamParser.toSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
