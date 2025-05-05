package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.impl.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.service.ProductService;
import mate.academy.springboot.swagger.util.SortService;
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
    private final ProductDtoMapper dtoMapper;
    private final ProductService productService;
    private final SortService sortService;

    public ProductController(ProductDtoMapper dtoMapper,
                             ProductService productService,
                             SortService sortService) {
        this.dtoMapper = dtoMapper;
        this.productService = productService;
        this.sortService = sortService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return dtoMapper.toDto(productService.save(dtoMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product for by id")
    @ApiResponse(code = 404, message = "Product not found")
    public ProductResponseDto getById(@PathVariable Long id) {
        return dtoMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product for by id")
    public void deleteById(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product with using id and new data about product")
    @ApiResponse(code = 404, message = "Product not found")
    public ProductResponseDto updateById(@PathVariable Long id,
                                         @RequestBody ProductRequestDto requestDto) {
        Product product = dtoMapper.toModel(requestDto);
        product.setId(id);
        return dtoMapper.toDto(productService.update(product));
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "Get list of products")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20 elements on page") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0 page") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is sort by id") String sortBy) {
        List<Sort.Order> orders = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(orders));
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/findAll/prices")
    @ApiOperation(value = "Get the list of products that are between prices")
    public List<ProductResponseDto> findAllByPrices(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is 20 elements on page")Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is 0 page")Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default value is sort by id")String sortBy) {
        List<Sort.Order> orders = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(orders));
        return productService.findAllByPrice(from, to, pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
