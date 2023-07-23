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
import mate.academy.springboot.swagger.service.Parser;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
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
    private final DtoMapper<ProductResponseDto, ProductRequestDto, Product> productMapper;
    private final ProductService productService;
    private final Parser<Sort.Order, String> urlParser;

    @PostMapping
    @ApiOperation(value = "This endpoint is used for creating a new product")
    public ProductResponseDto create(@RequestBody @ApiParam(value = "This parameter"
            + " expects a body into your query with"
            + " fields: title, price") ProductRequestDto requestDto) {
        return productMapper
                .mapToDto(productService.create(productMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "This endpoint is used for receiving a product by id")
    public ProductResponseDto get(@PathVariable @ApiParam(value = "This parameter expects an"
            + " identifier of the desired product which will be received") Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "This endpoint is used for removing a product by id")
    public void delete(@PathVariable @ApiParam(value = "This parameter expects an"
            + " identifier of the desired product which will be removed") Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "This endpoint is used for updating a product by id")
    public ProductResponseDto update(@PathVariable @ApiParam(value = "This parameter expects an"
            + " identifier of the desired product which will be updated") Long id,
                                     @RequestBody @ApiParam(value = "This parameter expects a"
                                             + " body into your query with"
                                             + " fields: title, price")
                                     ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @GetMapping("/all")
    @ApiOperation(value = "This endpoint is used for receiving all products."
            + "It supports pagination and sorting")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "10")
                                               @ApiParam(value = "This parameter expects a"
                                                       + " number of products which will be"
                                                       + " displayed on the page. The value"
                                                       + " by default is 10") Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "This parameter expects a page."
                                                   + " The value by default is 0.") Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "This parameter expects a"
                                                       + " proposition how to sort products. The"
                                                       + " value by default is id") String sortBy) {
        Sort sort = Sort.by(urlParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "This endpoint is used for receiving all products where price"
            + " is between two values. It supports pagination and sorting")
    public List<ProductResponseDto> getAllByPrice(@RequestParam @ApiParam
            (value = "This parameter expects a value of price from which products will be"
                    + " selected") BigDecimal from,
                                                  @RequestParam @ApiParam
                                                          (value = "This parameter expects a"
                                                          + " value of price up to which products"
                                                          + " will be selected") BigDecimal to,
                                                  @RequestParam (defaultValue = "10")
                                                      @ApiParam(value = "This parameter expects a"
                                                              + " number of products which will be"
                                                              + " displayed on the page. The value"
                                                              + " by default is 10") Integer count,
                                                  @RequestParam (defaultValue = "0")
                                                      @ApiParam(value = "This parameter expects"
                                                              + " a page. The value by default"
                                                              + " is 0.") Integer page,
                                                  @RequestParam (defaultValue = "id")
                                                      @ApiParam(value = "This parameter expects a"
                                                              + " proposition how to sort products."
                                                              + " The value by default is id")
                                                      String sortBy) {
        Sort sort = Sort.by(urlParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
