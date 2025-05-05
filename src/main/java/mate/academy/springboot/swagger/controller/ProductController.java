package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.dto.mapper.ResponseDtoMapper;
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
    private final SortingService sortingService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;

    public ProductController(ProductService productService,
                             SortingService sortingService,
                             RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper) {
        this.productService = productService;
        this.sortingService = sortingService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(requestDtoMapper.mapToModel(productRequestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = requestDtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        productService.update(product);
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products by price")
    public List<ProductResponseDto> getAllBetweenPrice(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "Default value is `20`") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Default value is `0`") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "Default value is `id`") String sortBy) {
        Sort sort = sortingService.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPrice(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "Default value is `20`") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Default value is `0`") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "Default value is `id`") String sortBy) {
        Sort sort = sortingService.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
