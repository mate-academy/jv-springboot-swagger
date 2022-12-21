package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoRequestMapper;
import mate.academy.springboot.swagger.mapper.DtoResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
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
    private final SortService sortService;
    private final DtoRequestMapper<ProductRequestDto, Product> productRequestMapper;
    private final DtoResponseMapper<ProductResponseDto, Product> productResponseMapper;

    public ProductController(ProductService productService,
                             SortService sortService,
                             DtoRequestMapper<ProductRequestDto, Product> productRequestMapper,
                             DtoResponseMapper<ProductResponseDto, Product> productResponseMapper) {
        this.productService = productService;
        this.sortService = sortService;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productRequestMapper.fromDto(requestDto);
        productService.save(product);
        return productResponseMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productResponseMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@RequestParam Long id,
                                     @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productRequestMapper.fromDto(requestDto);
        product.setId(id);
        productService.update(product);
        return productResponseMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get list of products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                               @ApiParam(value = "Default value is `20`")
                                               Integer count,
                                           @RequestParam(defaultValue = "0")
                                               @ApiParam(value = "Default value is `0`")
                                               Integer page,
                                           @RequestParam(defaultValue = "id")
                                               @ApiParam(value = "Default value is `id`")
                                               String sortBy) {
        List<Sort.Order> orders = sortService.sort(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price-range")
    @ApiOperation(value = "Get list of products by price range")
    public List<ProductResponseDto> getByPriceRange(@RequestParam Double from,
                                                    @RequestParam Double to,
                                                    @RequestParam(defaultValue = "20")
                                                        @ApiParam(value = "Default value is `20`")
                                                        Integer count,
                                                    @RequestParam(defaultValue = "0")
                                                        @ApiParam(value = "Default value is `0`")
                                                        Integer page,
                                                    @RequestParam(defaultValue = "id")
                                                        @ApiParam(value = "Default value is `id`")
                                                        String sortBy) {
        List<Sort.Order> orders = sortService.sort(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}
