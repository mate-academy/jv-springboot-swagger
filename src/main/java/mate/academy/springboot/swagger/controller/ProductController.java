package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortUtil;
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
    private static final String[] TITLE = {"Bread", "Butter", "Cocoa",
            "Coffee", "Apple", "Cucumber"};
    private static final int MAX_PRICE = 10000;
    private static final int NUMBER_OF_PRODUCTS = 50;
    private static final Random RANDOM = new Random();
    private final ProductService productService;
    private final RequestDtoMapper<Product, ProductRequestDto> requestDtoMapper;
    private final ResponseDtoMapper<Product, ProductResponseDto> responseDtoMapper;
    private final SortUtil sortUtil;

    public ProductController(ProductService productService,
                             RequestDtoMapper<Product, ProductRequestDto> requestDtoMapper,
                             ResponseDtoMapper<Product, ProductResponseDto> responseDtoMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.sortUtil = sortUtil;
    }

    @PostConstruct
    public void inject() {
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            productService.save(generateProduct());
        }
    }

    @PostMapping("/create")
    @ApiOperation(value = "create a new product")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return responseDtoMapper.mapToDto(
                productService.save(
                        requestDtoMapper.mapToModel(productRequestDto)));
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "delete product by id")
    public void deleteProductById(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody ProductRequestDto productRequestDto) {
        Product product = requestDtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "get list of product")
    public List<ProductResponseDto> getProducts(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is id") String sortBy) {
        List<Sort.Order> sortParams = sortUtil.getSortParams(sortBy);
        Sort sort = Sort.by(sortParams);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get-by-price")
    @ApiOperation(value = "get list of product by price between two values")
    public List<ProductResponseDto> getProductsByPrice(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is id") String sortBy,
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to) {
        List<Sort.Order> sortParams = sortUtil.getSortParams(sortBy);
        Sort sort = Sort.by(sortParams);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private Product generateProduct() {
        Product product = new Product();
        product.setTitle(TITLE[RANDOM.nextInt(TITLE.length)]);
        product.setPrice(BigDecimal.valueOf(RANDOM.nextInt(MAX_PRICE)));
        return product;
    }
}
