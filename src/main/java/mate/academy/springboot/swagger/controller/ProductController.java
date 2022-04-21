package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.repository.ProductRepository;
import mate.academy.springboot.swagger.util.OrderUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderUtil orderUtil;

    public ProductController(
            ProductRepository productRepository,
            ProductMapper productMapper, OrderUtil orderUtil) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.orderUtil = orderUtil;
    }

    @PostMapping
    @ApiOperation(value = "create new product")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toProductResponseDto(productRepository
            .save(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        return productMapper.toProductResponseDto(productRepository.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "update product")
    public ProductResponseDto updateProduct(@PathVariable Long id,
            @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toProductResponseDto(product);
    }

    @GetMapping
    @ApiOperation(value = "find list products with pagination and sorting")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                          @RequestParam (defaultValue = "0") Integer page,
                                          @RequestParam (defaultValue = "id") String sortBy) {
        return productRepository.findAll(orderUtil.makeOrder(count, page, sortBy)).stream()
            .map(productMapper::toProductResponseDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/by_price")
    @ApiOperation(value = "find list product in the range between two values")
    public List<ProductResponseDto> findAllByPriceBetweenAll(@RequestParam BigDecimal from,
                                                           @RequestParam BigDecimal to) {
        return productRepository.findProductsByPriceBetween(from, to).stream()
        .map(productMapper::toProductResponseDto)
        .collect(Collectors.toList());
    }
}
