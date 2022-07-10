package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
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
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortingService sortingService;

    public ProductController(ProductService productService,
                             ProductMapper productMapper, SortingService sortingService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortingService = sortingService;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper
                .mapToProduct(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping
    @ApiOperation(value = "get list of products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                            @ApiParam(value = "default value is `20`")
                                            Integer count,
                                            @RequestParam (defaultValue = "0")
                                            @ApiParam(value = "default value is `0`")
                                            Integer page,
                                            @RequestParam (defaultValue = "id")
                                            @ApiParam(value = "default sorting by `id`")
                                            String sortBy) {
        Sort sort = Sort.by(sortingService.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(e -> productMapper.mapToDto(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get list of products with specified price range")
    public List<ProductResponseDto> findAllByPrice(@RequestParam(name = "from")
                                                   BigDecimal from,
                                                   @RequestParam(name = "to")
                                                   BigDecimal to,
                                                   @RequestParam (defaultValue = "20")
                                                   @ApiParam(value = "default value is `20`")
                                                   Integer count,
                                                   @RequestParam (defaultValue = "0")
                                                   @ApiParam(value = "default value is `0`")
                                                   Integer page,
                                                   @RequestParam (defaultValue = "id")
                                                   @ApiParam(value = "default sorting by `id`")
                                                   String sortBy) {
        Sort sort = Sort.by(sortingService.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(e -> productMapper.mapToDto(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a product by specified id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));

    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product with specified id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToProduct(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.save(product));
    }

    @DeleteMapping("/id")
    public void deleteById(@RequestParam Long id) {
        productService.deleteById(id);
    }
}
