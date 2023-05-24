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
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.SorterUtil;
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
@AllArgsConstructor
public class ProductController {
    private static final String PRODUCTS_PER_PAGE = "10";
    private static final String PAGE = "0";
    private static final String SORT_FIELD = "title";
    private final ProductService productService;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> productMapper;
    private final SorterUtil sorterUtil;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        productService.add(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.get(id);
        return productMapper.mapToDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update an information about product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "get all products with pagination and ability to sort "
            + "by title in ASC or DESC order")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = PRODUCTS_PER_PAGE)
                                                @ApiParam(value = "default value is "
                                                        + PRODUCTS_PER_PAGE) Integer count,
                                 @RequestParam (defaultValue = PAGE)
                                 @ApiParam(value = "default value is " + PAGE) Integer page,
                                 @RequestParam (defaultValue = SORT_FIELD)
                                                @ApiParam(value = "default value is "
                                                        + SORT_FIELD) String sortBy) {
        return productService.getAll(PageRequest.of(page, count,
                        sorterUtil.getSort(sortBy))).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products by price between two values with pagination "
            + "and ability to sort by title in ASC or DESC order")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam (defaultValue = PRODUCTS_PER_PAGE)
                                                      @ApiParam(value = "default value is "
                                                              + PRODUCTS_PER_PAGE) Integer count,
                                                  @RequestParam (defaultValue = PAGE)
                                                      @ApiParam(value = "default value is "
                                                              + PAGE) Integer page,
                                                  @RequestParam (defaultValue = SORT_FIELD)
                                                      @ApiParam(value = "default value is "
                                                              + SORT_FIELD) String sortBy) {
        return productService.getAllByPrice(from, to,
                        PageRequest.of(page, count, sorterUtil.getSort(sortBy))).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
