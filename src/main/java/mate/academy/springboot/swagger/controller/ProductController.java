package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import util.PageParseUtil;
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
    private static final String DEFAULT_SORTING_VALUE = "title:DESC";
    private static final String DEFAULT_VALUE_PAGE = "0";
    private static final String DEFAULT_VALUE_COUNT = "5";
    private final ProductService productService;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    
    public ProductController(ProductService productService,
                             ResponseDtoMapper<ProductResponseDto,
                                     Product> responseDtoMapper,
                             RequestDtoMapper<ProductRequestDto,
                                     Product> requestDtoMapper) {
        this.productService = productService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @ApiOperation(value = "Add new product to DB by input parameters")
    @PostMapping
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.save(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @ApiOperation(value = "Get product from DB by ID")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return responseDtoMapper.mapToDto(product);
    }

    @ApiOperation(value = "Update product from DB by ID and input parameters")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = requestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.update(product));
    }

    @ApiOperation(value = "Delete product from DB by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "Get all products from DB, with sorting and pagination ability")
    @GetMapping
    List<ProductResponseDto> findAll(@RequestParam(defaultValue
            = DEFAULT_VALUE_COUNT) Integer count,
                                              @RequestParam(defaultValue
                                                      = DEFAULT_VALUE_PAGE) Integer page,
                                              @RequestParam(defaultValue
                                                      = DEFAULT_SORTING_VALUE) String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count,
                Sort.by(PageParseUtil.parse(sortBy)));
        return productService.findAll(pageRequest)
        .stream()
        .map(responseDtoMapper::mapToDto)
        .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all products from DB by price between, with sorting and pagination ability")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam(defaultValue
            = DEFAULT_VALUE_COUNT) Integer count,
                                                          @RequestParam(defaultValue
                                                                  = DEFAULT_VALUE_PAGE)
                                                          Integer page,
                                                          @RequestParam(defaultValue
                                                                  = DEFAULT_SORTING_VALUE)
                                                              String sortBy,
                                                          @RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to) {
        PageRequest pageRequest = PageRequest.of(page, count,
                Sort.by(PageParseUtil.parse(sortBy)));
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
