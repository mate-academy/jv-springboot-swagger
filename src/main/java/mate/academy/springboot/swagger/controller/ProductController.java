package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.impl.ProductMapper;
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
public class ProductController {
    private static final String FIELD_SEPARATOR = ";";
    private static final String ORDER_SEPARATOR = ":";
    private static final String DEFAULT_ALL_PRODUCTS_ROWS_COUNT = "10";
    private static final String DEFAULT_ALL_PRODUCTS_PAGE_NUMBER = "0";
    private static final String DEFAULT_ALL_PRODUCTS_SORT_BY = "id";
    private static final String DEFAULT_PRODUCTS_BY_PRICE_ROWS_COUNT = "10";
    private static final String DEFAULT_PRODUCTS_BY_PRICE_PAGE_NUMBER = "0";
    private static final String DEFAULT_PRODUCTS_BY_PRICE_SORT_BY = "id";
    private static final String API_PARAM_SORT_BY = "field1["
            + ORDER_SEPARATOR + "ASC(default)|DESC]["
            + FIELD_SEPARATOR + "field2["
            + ORDER_SEPARATOR + "ASC|DESC]]";
    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductController(ProductMapper productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping
    @ApiOperation(value = "Add new Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.mapToDto(
                productService.save(
                        productMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Product by id")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productMapper.mapToDto(
                productService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "Get all Products with pagination and ability to sort in ASC|DESC order")
    public List<ProductResponseDto> getAllProducts(
            @RequestParam (defaultValue = DEFAULT_ALL_PRODUCTS_ROWS_COUNT)
            @ApiParam(value = "Default count is " + DEFAULT_ALL_PRODUCTS_ROWS_COUNT)
            Integer rows,
            @RequestParam (defaultValue = DEFAULT_ALL_PRODUCTS_PAGE_NUMBER)
            @ApiParam(value = "Default number " + DEFAULT_ALL_PRODUCTS_PAGE_NUMBER)
            Integer page,
            @RequestParam (defaultValue = DEFAULT_ALL_PRODUCTS_SORT_BY)
            @ApiParam(value = API_PARAM_SORT_BY
                    + " Default is " + DEFAULT_ALL_PRODUCTS_SORT_BY)
            String sortBy) {
        Sort sort = Sort.by(buildSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, rows, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price_between")
    @ApiOperation(value = "Get Products where price is between 'fromPrice' and 'toPrice' "
            + "with pagination and ability to sort in ASC|DESC order")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal fromPrice,
            @RequestParam BigDecimal toPrice,
            @RequestParam (defaultValue = DEFAULT_PRODUCTS_BY_PRICE_ROWS_COUNT)
            @ApiParam(value = "Default count is " + DEFAULT_PRODUCTS_BY_PRICE_ROWS_COUNT)
            Integer rows,
            @RequestParam (defaultValue = DEFAULT_PRODUCTS_BY_PRICE_PAGE_NUMBER)
            @ApiParam(value = "Default number is " + DEFAULT_PRODUCTS_BY_PRICE_PAGE_NUMBER)
            Integer page,
            @RequestParam (defaultValue = DEFAULT_PRODUCTS_BY_PRICE_SORT_BY)
            @ApiParam(value = API_PARAM_SORT_BY
                    + " Default is " + DEFAULT_PRODUCTS_BY_PRICE_SORT_BY)
            String sortBy) {
        Sort sort = Sort.by(buildSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, rows, sort);
        return productService.findAllByPriceBetween(fromPrice, toPrice, pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody ProductRequestDto requestDto) {
        Product product = productService.getById(id);
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());
        return productMapper.mapToDto(
                productService.save(product));
    }

    private List<Sort.Order> buildSortOrders(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        Arrays.stream(sortBy.split(FIELD_SEPARATOR))
                .map(f -> f.split(ORDER_SEPARATOR))
                .forEach(f -> orders.add(f.length == 1 ? new Sort.Order(Sort.Direction.ASC, f[0]) :
                        new Sort.Order(Sort.Direction.valueOf(f[1]),f[0])));
        return orders;
    }
}
