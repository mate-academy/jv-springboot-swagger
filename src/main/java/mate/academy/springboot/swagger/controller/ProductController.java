package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductRequestDtoMapper;
import mate.academy.springboot.swagger.mapper.ProductResponseDtoMapper;
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
    private ProductService productService;
    private ProductRequestDtoMapper requestDtoMapper;
    private ProductResponseDtoMapper responseDtoMapper;
    private SortingService sortingService;

    public ProductController(ProductService productService,
                             ProductRequestDtoMapper requestDtoMapper,
                             ProductResponseDtoMapper responseDtoMapper,
                             SortingService sortingService) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.sortingService = sortingService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return responseDtoMapper.toDto(productService.create(
                requestDtoMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.getById(id);
        return responseDtoMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id and request body")
    ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.toModel(requestDto);
        product.setId(id);
        return responseDtoMapper.toDto(productService.create(product));
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                     @ApiParam(value = "Default value is 20")
                                     Integer elements,
                                     @RequestParam(defaultValue = "0")
                                     @ApiParam(value = "Default value is 0")
                                     Integer page,
                                     @RequestParam(defaultValue = "id")
                                     @ApiParam(value = "Default value is id")
                                     String sortBy) {

        Sort sort = Sort.by(sortingService.getAllSorting(sortBy));
        PageRequest pageRequest = PageRequest.of(page, elements, sort);
        return productService.getAll(pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    List<ProductResponseDto> findByPrices(@RequestParam(defaultValue = "20")
                                          @ApiParam(value = "Default value is 20")
                                          Integer elements,
                                          @RequestParam(defaultValue = "0")
                                          @ApiParam(value = "Default value is 0")
                                          Integer page,
                                          @RequestParam(defaultValue = "id")
                                          @ApiParam(value = "Default value is id")
                                          String sortBy,
                                          @RequestParam BigDecimal from,
                                          @RequestParam BigDecimal to) {
        Sort sort = Sort.by(sortingService.getAllSorting(sortBy));
        PageRequest pageRequest = PageRequest.of(page, elements, sort);
        return productService.getAllByPrice(from, to, pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
