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
import mate.academy.springboot.swagger.service.SortParamsParser;
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
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final SortParamsParser sortParamsParser;

    public ProductController(ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper,
                             SortParamsParser sortParamsParser) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.sortParamsParser = sortParamsParser;
    }

    @PostMapping
    @ApiOperation(value = "create a new Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.toModel(requestDto);
        return responseDtoMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get Product by ID")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete Product by ID")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.toModel(requestDto);
        product.setId(id);
        return responseDtoMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "get products sorted (ASC or DESC) list by pages")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "0")
            @ApiParam("default value '0'") Integer page,
            @RequestParam(defaultValue = "20")
            @ApiParam("default value '20'") Integer count,
            @RequestParam(defaultValue = "title")
            @ApiParam("default value 'title'") String sortBy) {
        Sort sort = sortParamsParser.getSortForParams(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiParam(value = "get products in price range")
    public List<ProductResponseDto> getAllByPrice(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "0")
            @ApiParam("default value '0'") Integer page,
            @RequestParam(defaultValue = "20")
            @ApiParam("default value '20'") Integer count,
            @RequestParam(defaultValue = "title")
            @ApiParam("default value 'title'") String sortBy) {
        Sort sort = sortParamsParser.getSortForParams(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
