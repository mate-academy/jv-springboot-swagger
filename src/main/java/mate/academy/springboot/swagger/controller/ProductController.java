package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.StringParser;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @ApiOperation(value = "create a new product")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.responseDto(productService
                .create(productMapper.toModel(productRequestDto)));
    }

    @ApiOperation(value = "get the product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.responseDto(productService.getById(id));
    }

    @ApiOperation(value = "delete the product by id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "update the product")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        product.setId(id);
        return productMapper.responseDto(productService.update(product));
    }

    @ApiOperation(value = "get products between the prices")
    @GetMapping("/price")
    public List<ProductResponseDto> findAllProductsBetweenPrices(
            @RequestParam
            @ApiParam(value = "Lower boundary of the price range") BigDecimal priceFrom,
            @RequestParam
            @ApiParam(value = "Upper boundary of the price range") BigDecimal priceTo,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "Quantity rows per page. Default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "Number of the page. Default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "Sorting field name. Default value is 'id'") String sortBy) {
        Sort sort = Sort.by(new StringParser().getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService
                .getProductsByPriceBetween(priceFrom, priceTo, pageRequest)
                .stream()
                .map(productMapper::responseDto)
                .collect(Collectors.toList());

    }

    @ApiOperation(value = "get all products")
    @GetMapping()
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
            @ApiParam(value = "Quantity rows per page. Default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "Number of the page. Default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "Sorting field name. Default value is 'id'") String sortBy) {
        Sort sort = Sort.by(new StringParser().getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService
                .findAll(pageRequest)
                .stream()
                .map(productMapper::responseDto)
                .collect(Collectors.toList());
    }
}
