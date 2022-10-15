package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.util.Parser;
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
    private final ProductDtoMapper mapper;
    private final Parser sortParser;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.create(mapper.toModel(requestDto));
        return mapper.toDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update the product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = mapper.toModel(requestDto);
        product.setId(id);
        return mapper.toDto(productService.create(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get the product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete the product")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/price")
    @ApiOperation(value = "get all products by price")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam
                                                         @ApiParam(value = "beginning value")
                                                                 BigDecimal priceFrom,
                                                         @RequestParam
                                                         @ApiParam(value = "ending value value")
                                                                 BigDecimal priceTo,
                                                         @RequestParam(defaultValue = "20")
                                                         @ApiParam(value = "default value is 20")
                                                                 Integer count,
                                                         @RequestParam(defaultValue = "0")
                                                         @ApiParam(value = "default value is 0")
                                                                 Integer page,
                                                         @RequestParam(defaultValue = "id")
                                                         @ApiParam(value
                                                                 = "default value is "
                                                                 + "sorting by id DESC order")
                                                                 String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortParser.parse(sortBy));
        return productService
                .findAllByPriceBetween(priceFrom, priceTo, pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                           @ApiParam(value = "default value is 20")
                                                   Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "default value is 0")
                                                   Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value
                                                   = "default value is "
                                                   + "sorting by id DESC order")
                                                   String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortParser.parse(sortBy));
        return productService.findAll(pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    @ApiOperation(value = "inject products to db to test")
    public void saveProducts() {
        Product firstPhone = new Product();
        firstPhone.setTitle("iPhone 14");
        firstPhone.setPrice(BigDecimal.valueOf(1000));
        productService.create(firstPhone);

        Product secondPhone = new Product();
        secondPhone.setTitle("iPhone 14 Pro");
        secondPhone.setPrice(BigDecimal.valueOf(1300));
        productService.create(secondPhone);

        Product thirdPhone = new Product();
        thirdPhone.setTitle("iPhone 13");
        thirdPhone.setPrice(BigDecimal.valueOf(800));
        productService.create(thirdPhone);
    }
}
