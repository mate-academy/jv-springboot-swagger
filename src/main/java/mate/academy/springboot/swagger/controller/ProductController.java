package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
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
    private final SortingService sortingService;
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<Product, ProductResponseDto> responseDtoMapper;

    public ProductController(SortingService sortingService,
                             ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product>
                                     requestDtoMapper,
                             ResponseDtoMapper<Product, ProductResponseDto>
                                     responseDtoMapper) {
        this.sortingService = sortingService;
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                                @ApiParam(value = "default value is `20`")
                                                Integer count,
                                            @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "default value is `0`")
                                                Integer page,
                                            @RequestParam (defaultValue = "title")
                                                @ApiParam(value = "default value is `title`")
                                                String sortBy) {
        Sort sort = Sort.by(sortingService.createSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return responseDtoMapper.toDto(productService.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create product")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        return responseDtoMapper.toDto(productService
                .save(requestDtoMapper.toModel(requestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list by price between")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam (defaultValue = "20")
                                                              @ApiParam(value =
                                                                      "default value is `20`")
                                                              Integer count,
                                                          @RequestParam (defaultValue = "0")
                                                              @ApiParam(value =
                                                                      "default value is `0`")
                                                              Integer page,
                                                          @RequestParam (defaultValue = "title")
                                                              @ApiParam(value =
                                                                      "default value is `title`")
                                                              String sortBy,
                                                          @RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to) {
        Sort sort = Sort.by(sortingService.createSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.toModel(requestDto);
        product.setId(id);
        return responseDtoMapper.toDto(productService.save(product));
    }
}
