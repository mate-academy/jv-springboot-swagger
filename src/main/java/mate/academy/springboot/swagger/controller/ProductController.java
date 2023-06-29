package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortOrderUtil;
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
    private final RequestDtoMapper<ProductRequestDto, Product> requestMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseMapper;
    private final SortOrderUtil sortOrderUtil;

    public ProductController(ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product> requestMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> responseMapper,
                             SortOrderUtil sortOrderUtil) {
        this.productService = productService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
        this.sortOrderUtil = sortOrderUtil;
    }

    @ApiOperation(value = "Add a new product")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(product);
    }

    @ApiOperation(value = "Get a product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return responseMapper.toDto(product);
    }

    @ApiOperation(value = "Delete a product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ApiOperation(value = "Update a product by id")
    @PutMapping("/{id}")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                      @PathVariable Long id) {
        Product product = requestMapper.toModel(requestDto);
        product.setId(id);
        return responseMapper.toDto(productService.save(product));
    }

    @ApiOperation(value = "Get all products with pagination "
            + "and sorting options (ASC & DESC) for title or price")
    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "10")
                                               @ApiParam(value = "default value is '10'")
                                               Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "default value is '0'")
                                           Integer page,
                                           @RequestParam(defaultValue = "title")
                                               @ApiParam(value = "default value is 'title'")
                                               String sortBy) {
        Sort sort = sortOrderUtil.getSorter(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all products having price in range from "
            + "@param 'from' to @param 'to' inclusively "
            + "with pagination and sorting options (ASC & DESC) for title or price")
    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam(defaultValue = "10")
                                                      @ApiParam(value = "default value is '10'")
                                                      Integer count,
                                                         @RequestParam(defaultValue = "0")
                                                      @ApiParam(value = "default value is '0'")
                                                      Integer page,
                                                         @RequestParam(defaultValue = "title")
                                                      @ApiParam(value = "default value is 'title'")
                                                      String sortBy) {
        Sort sort = sortOrderUtil.getSorter(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}
