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
import mate.academy.springboot.swagger.service.SortParamParser;
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
    private final ProductService productService;
    private final SortParamParser sortParamParser;
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;

    public ProductController(ProductService productService,
            SortParamParser sortParamParser,
            RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper,
            ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper) {
        this.productService = productService;
        this.sortParamParser = sortParamParser;
        this.productRequestDtoMapper = productRequestDtoMapper;
        this.productResponseDtoMapper = productResponseDtoMapper;
    }

    @PostMapping
    @ApiOperation("Create a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService
                .add(productRequestDtoMapper.mapToModel(productRequestDto));
        return productResponseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productResponseDtoMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productRequestDtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        return productResponseDtoMapper.mapToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation("Get all products list")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "0") @ApiParam("default value is 0") Integer page,
            @RequestParam(defaultValue = "20") @ApiParam("default value is 20") Integer size,
            @RequestParam(defaultValue = "id") @ApiParam("default value is 'id'") String sortBy) {
        Sort sort = sortParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/byPrice")
    @ApiOperation("Get all products list by price between")
    public List<ProductResponseDto> getAllByPrice(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "0") @ApiParam("default value is 0") Integer page,
            @RequestParam(defaultValue = "20") @ApiParam("default value is 20") Integer size,
            @RequestParam(defaultValue = "id") @ApiParam("default value is 'id'") String sortBy) {
        Sort sort = sortParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAllByPrice(from, to, pageRequest)
                .stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
