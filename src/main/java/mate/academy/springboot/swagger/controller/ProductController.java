package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortDirectionParser;
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
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;

    public ProductController(
            ProductService productService,
            ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper,
            RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper) {
        this.productService = productService;
        this.responseDtoMapper = responseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "creates a new product and adds it to db")
    public ProductResponseDto add(@RequestBody ProductRequestDto productDto) {
        Product product = requestDtoMapper.mapToModel(productDto);
        return responseDtoMapper.mapToDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "returns a product from db by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "sets all product fields"
            + " with parameters fields and returns updated product ")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productDto) {
        Product product = requestDtoMapper.mapToModel(productDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deletes a product from db by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/byPrice")
    @ApiOperation(value = "returns all products from db with price in range,"
            + " able to sort ASC/DESC,set page and change number of products on a page")
    public List<ProductResponseDto> findAllByPriceBetween(
            @ApiParam(value = "lower bound of product price")
            @RequestParam BigDecimal from,
            @ApiParam(value = "upper bound of product price")
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "sorting field and order for sorting, default value = id")
            String sortBy,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "number of products on a page, default value = 20")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "page index, default value = 0")
            Integer page) {
        Sort sort = SortDirectionParser.by(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(pageRequest, from, to).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "returns all products from db with ability to "
            + "sort ASC/DESC,set page and change number of products on a page")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "sorting field and order for sorting, default value = id")
            String sortBy,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "number of products on a page, default value = 20")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "page index, default value = 0")
            Integer page) {
        Sort sort = SortDirectionParser.by(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
