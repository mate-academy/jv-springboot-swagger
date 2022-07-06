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
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.util.SortProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productDtoMapper;
    private final SortProvider sortProvider;

    public ProductController(ProductService productService,
                             ProductMapper productDtoMapper,
                             SortProvider sortProvider) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
        this.sortProvider = sortProvider;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto add(@RequestParam ProductRequestDto requestDto) {
        return productDtoMapper.toDto(productService.add(productDtoMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productDtoMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void update(@PathVariable Long id, @RequestParam ProductRequestDto requestDto) {
        Product product = productDtoMapper.toModel(requestDto);
        product.setId(id);
        productService.add(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "0")
                                               @ApiParam(value = "Specify page to show") int page,
                                @RequestParam(defaultValue = "20")
                                @ApiParam(value = "Specify amount of items on one page") int size,
                                @RequestParam(defaultValue = "id")
                                               @ApiParam(value = "Specify sorting options")
                                               String sort) {
        Sort preparedSort = Sort.by(sortProvider.provideSort(sort));
        Pageable pageable = PageRequest.of(page, size, preparedSort);
        return productService.getAll(pageable)
                .stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam
                                                      @ApiParam(value = "Price items start at")
                                                      BigDecimal from,
                                                  @RequestParam @ApiParam(value =
                                                          "Price items start at")
                                                  BigDecimal to,
                                                  @RequestParam(defaultValue = "0")
                                                      @ApiParam(value = "Specify page to show")
                                                      int page,
                                                  @RequestParam(defaultValue = "20")
                                                      @ApiParam(value =
                                                              "Specify amount of items on one page")
                                                      int size,
                                                  @RequestParam(defaultValue = "id")
                                                      @ApiParam(value = "Specify sorting options")
                                                      String sort) {
        Sort preparedSort = Sort.by(sortProvider.provideSort(sort));
        Pageable pageable = PageRequest.of(page, size, preparedSort);
        return productService.getAllPriceBetween(from, to, pageable)
                .stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
