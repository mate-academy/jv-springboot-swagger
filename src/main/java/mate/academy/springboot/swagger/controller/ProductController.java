package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.Mapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.PageRequestPrepare;
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
public class ProductController {
    private ProductService productService;
    private Mapper<Product, ProductRequestDto, ProductResponseDto> mapper;

    public ProductController(ProductService productService, Mapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(mapper.toModel(requestDto));
        return mapper.toResponseDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product")
    public ProductResponseDto update(@PathVariable @ApiParam(value = "Product id") Long id,
                          @RequestBody ProductRequestDto requestDto) {
        Product product = productService.getById(id);
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return mapper.toResponseDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable @ApiParam(value = "Product id") Long id) {
        return mapper.toResponseDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable @ApiParam(value = "Product id") Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                                @ApiParam(value = "Elements on page. "
                                                        + "Default value is `20`")
                                                Integer size,
                                            @RequestParam (defaultValue = "0")
                                            @ApiParam(value = "Number of page. "
                                                    + "Default value is `0`")
                                                Integer page,
                                            @RequestParam (defaultValue = "id")
                                            @ApiParam(value = "By default sorting by "
                                                    + "`id` order `DESC`")
                                                String sortBy) {

        return productService.getAll(PageRequestPrepare
                .getPageRequestObj(size, page, sortBy))
                .stream()
                .map(p -> mapper.toResponseDto(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products by price between")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal priceFrom,
                                                          @RequestParam BigDecimal priceTo,
                                                          @RequestParam (defaultValue = "20")
                                                              @ApiParam(value = "Elements on page. "
                                                                      + "Default value is `20`")
                                                                      Integer size,
                                                          @RequestParam (defaultValue = "0")
                                                              @ApiParam(value =
                                                                      "Number of page. Default "
                                                                              + "value is `0`")
                                                                      Integer page,
                                                          @RequestParam (defaultValue = "id")
                                                          @ApiParam(value = "By default sorting by "
                                                                  + "`id` order `DESC`")
                                                                      String sortBy) {
        return productService.findAllByPriceBetween(priceFrom, priceTo,
                PageRequestPrepare.getPageRequestObj(size, page, sortBy))
                .stream()
                .map(p -> mapper.toResponseDto(p))
                .collect(Collectors.toList());
    }
}
