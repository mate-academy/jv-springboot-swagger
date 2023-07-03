package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
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
    private final ProductService productService;
    private final SortingService sortingService;

    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             SortingService sortingService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.sortingService = sortingService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get Product by ID")
    ProductResponseDto getById(@PathVariable
                               @ApiParam(value = "ID of the product")
                               Long id) {
        Product product = productService.get(id);
        return productMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete Product by ID")
    void delete(@PathVariable
                @ApiParam(value = "ID of the product")
                Long id) {
        productService.delete(id);
    }

    @PostMapping
    @ApiOperation(value = "create a new Product")
    ProductResponseDto save(@RequestBody
                            @Validated
                            @ApiParam(value = "object(DTO) of the product")
                            ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        Product savedProduct = productService.saveOrUpdate(product);
        return productMapper.toDto(savedProduct);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update Product by ID")
    ProductResponseDto update(@PathVariable
                              @ApiParam(value = "ID of the product")
                              Long id,
                              @RequestBody
                              @Validated
                              @ApiParam(value = "object(DTO) of the product")
                              ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        Product updatedProduct = productService.saveOrUpdate(product);
        return productMapper.toDto(updatedProduct);
    }

    @GetMapping
    @ApiOperation(value = "get all products with pagination and ability "
            + "to sort by price or by title in ASC or DESC order")
    List<ProductResponseDto> findAll(@RequestParam(defaultValue = "5")
                                     @ApiParam(value = "count of the objects on the "
                                             + "single page, default = 5")
                                     Integer count,
                                     @RequestParam(defaultValue = "0")
                                     @ApiParam(value = "page number, default = 0")
                                     Integer page,
                                     @RequestParam(defaultValue = "id")
                                     @ApiParam(value = "sorting params, default - id by ASC")
                                     String sortBy) {
        PageRequest pageRequest = sortingService.getPageRequest(count, page, sortBy);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "get all products where price is between "
            + "two values received as a RequestParam inputs. "
            + "Add pagination and ability to sort by price or by title in ASC or DESC order")
    List<ProductResponseDto> getAllByPrice(@RequestParam(value = "from")
                                           @ApiParam(value = "amount of minimum price")
                                           BigDecimal priceFrom,
                                           @RequestParam(value = "to")
                                           @ApiParam(value = "amount of maximum price")
                                           BigDecimal priceTo,
                                           @RequestParam(defaultValue = "5")
                                           @ApiParam(value = "count of the objects "
                                                   + "on the single page, default = 5")
                                           Integer count,
                                           @RequestParam(defaultValue = "1")
                                           @ApiParam(value = "page number, default = 0")
                                           Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value = "sorting params, default - id by ASC")
                                           String sortBy) {
        PageRequest pageRequest = sortingService.getPageRequest(count, page, sortBy);
        List<Product> products = productService.getAllBetweenTwoPrices(priceFrom,
                priceTo, pageRequest);
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }
}
