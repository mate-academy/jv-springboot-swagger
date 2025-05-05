package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.UrlSortParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private static final String PRODUCT_QUANTITY = "12";
    private static final String PAGE_NUMBER = "0";
    private static final String DEFAULT_SORT_FIELD = "id";
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UrlSortParser urlSortParser;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product product = productMapper.toProductModel(dto);
        Product savedProduct = productService.create(product);
        return productMapper.toProductResponseDto(savedProduct);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productMapper.toProductResponseDto(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productMapper.toProductModel(dto);
        product.setId(id);
        return productMapper.toProductResponseDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "get all products in pageable format with sorting")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = PAGE_NUMBER)
                                           @ApiParam(value = "default value is "
                                                   + PAGE_NUMBER) Integer page,
                                           @RequestParam(defaultValue = PRODUCT_QUANTITY)
                                           @ApiParam(value = "default value is "
                                                   + PRODUCT_QUANTITY) Integer pageSize,
                                           @RequestParam(defaultValue = DEFAULT_SORT_FIELD)
                                           @ApiParam(value = "default value is "
                                                   + DEFAULT_SORT_FIELD) String sortBy) {
        PageRequest pageRequest = createPageRequest(page, pageSize, sortBy);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products price between in pageable format with sorting")
    private List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = PAGE_NUMBER)
            @ApiParam(value = "default value is "
                    + PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = PRODUCT_QUANTITY)
            @ApiParam(value = "default value is "
                    + PRODUCT_QUANTITY) Integer pageSize,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD)
            @ApiParam(value = "default value is "
                    + DEFAULT_SORT_FIELD) String sortBy) {
        PageRequest pageRequest = createPageRequest(page, pageSize, sortBy);
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    private PageRequest createPageRequest(Integer page, Integer pageSize, String sortBy) {
        Sort sort = urlSortParser.getSort(sortBy);
        return PageRequest.of(page, pageSize, sort);
    }
}
