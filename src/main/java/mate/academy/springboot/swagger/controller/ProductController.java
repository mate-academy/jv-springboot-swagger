package mate.academy.springboot.swagger.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.ProductSortService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import javax.validation.Valid;
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
    private static final String DEFAULT_VALUE_PAGE = "1";
    private static final String DEFAULT_VALUE_COUNT = "5";
    private final ProductService productService;
    private final ProductSortService productSortService;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;

    public ProductController(ProductService productService,
                             ProductSortService productSortService,
                             ResponseDtoMapper<ProductResponseDto,
                                     Product> responseDtoMapper,
                             RequestDtoMapper<ProductRequestDto,
                                     Product> requestDtoMapper) {
        this.productService = productService;
        this.productSortService = productSortService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @PostMapping
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.save(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return responseDtoMapper.mapToDto(product);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = requestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    List<ProductResponseDto> findAll(@RequestParam(defaultValue
            = DEFAULT_VALUE_COUNT) Integer count,
                                              @RequestParam(defaultValue
                                                      = DEFAULT_VALUE_PAGE) Integer page,
                                              @RequestParam(defaultValue
                                                      = DEFAULT_SORTING_VALUE) String sort) {
        List<Sort.Order> orders = productSortService.sortBy(sort);
        Sort sortBy = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sortBy);
        return productService.findAll(pageRequest)
        .stream()
        .map(responseDtoMapper::mapToDto)
        .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam(defaultValue
            = DEFAULT_VALUE_COUNT) Integer count,
                                                          @RequestParam(defaultValue
                                                                  = DEFAULT_VALUE_PAGE) Integer page,
                                                          @RequestParam(defaultValue
                                                                  = DEFAULT_SORTING_VALUE) String sort,
                                                          @RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to) {
        List<Sort.Order> orders = productSortService.sortBy(sort);
        Sort sortBy = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sortBy);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
