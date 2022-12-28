package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.Parser;
import org.springframework.data.domain.PageRequest;
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
    private final ProductDtoMapper productDtoMapper;
    private final Parser parser;

    public ProductController(ProductService productService,
                             ProductDtoMapper productDtoMapper,
                             Parser parser) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
        this.parser = parser;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productDtoMapper.toDto(productService
                .save(productDtoMapper.fromDto(productRequestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(
            @PathVariable Long id,
            @RequestBody ProductRequestDto productRequestDto
    ) {
        Product product = productDtoMapper.fromDto(productRequestDto);
        product.setId(id);
        return productDtoMapper.toDto(productService.save(product));
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

    @GetMapping("/all")
    @ApiOperation(value = "Get a sorted list of products by parameters")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'")
            Integer page,
            @RequestParam (defaultValue = "10")
            @ApiParam(value = "default value is '10'")
            Integer count,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'")
            String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, count, parser.parse(sortBy));
        return productService.getAll(pageRequest)
                .stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get a sorted list of products by parameters and price")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'")
            Integer page,
            @RequestParam (defaultValue = "10")
            @ApiParam(value = "default value is '10'")
            Integer count,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is 'id'")
            String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, count, parser.parse(sortBy));
        return productService.getAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
