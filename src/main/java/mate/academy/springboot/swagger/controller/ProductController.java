package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoRequestMapper;
import mate.academy.springboot.swagger.mapper.DtoResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.parser.Parser;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    private final DtoRequestMapper<ProductRequestDto, Product> requestMapper;
    private final DtoResponseMapper<ProductResponseDto, Product> responseMapper;
    private final Parser<List<Sort.Order>, String> sortQueryParser;

    public ProductController(ProductService productService,
                             DtoRequestMapper<ProductRequestDto, Product> requestMapper,
                             DtoResponseMapper<ProductResponseDto, Product> responseMapper,
                             Parser<List<Sort.Order>, String> sortQueryParser) {
        this.productService = productService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
        this.sortQueryParser = sortQueryParser;
    }

    @PostMapping
    @ApiOperation("Create new product")
    public ProductResponseDto postProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = requestMapper.toModel(requestDto);
        return responseMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ProductResponseDto getProduct(@PathVariable Long id) {
        return responseMapper.toDto(productService.getById(id));
    }

    @GetMapping
    @ApiOperation("Get specified page of products")
    public List<ProductResponseDto> getProductPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "price") String sortBy) {
        Sort sort = Sort.by(sortQueryParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAll(pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation("Get specified page of products, in price range")
    public List<ProductResponseDto> getProductByPrice(
            @RequestParam(defaultValue = "0") BigDecimal min,
            @RequestParam(defaultValue = "1000000000") BigDecimal max,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "price") String sortBy
    ) {
        Sort sort = Sort.by(sortQueryParser.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getByPriceBetween(min, max, pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}
