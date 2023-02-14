package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.model.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.sort.SortParser;
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
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SortParser sortParser;
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;

    @PostMapping
    @ApiOperation("Create a new Product.")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return mapToDto(productService.save(mapToModel(requestDto, null)));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get Product by ID.")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapToDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete Product by ID.")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Product by ID.")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        return mapToDto(productService.save(mapToModel(requestDto, id)));
    }

    @GetMapping
    @ApiOperation("Get all Products with sort and pagination.")
    public List<ProductResponseDto> getAllBySort(@RequestParam(defaultValue = "20")
                                                 @ApiParam(value = "Default value is 20.")
                                                     Integer count,
                                                 @RequestParam(defaultValue = "0")
                                                 @ApiParam(value = "Default value is 0.")
                                                     Integer page,
                                                 @RequestParam(defaultValue = "id")
                                                 @ApiParam(value = "Default value is 'id'.")
                                                     String sortBy) {
        return mapToDtos(productService.findAll(PageRequest
                .of(page, count, sortParser.parse(sortBy))));
    }

    @GetMapping("/price")
    @ApiOperation("Get all Products with sort and pagination by price range.")
    public List<ProductResponseDto>
                    getAllByPriceBetweenAndSort(@RequestParam BigDecimal from,
                                                @RequestParam BigDecimal to,
                                                @RequestParam(defaultValue = "20")
                                                @ApiParam(value = "Default value is 20.")
                                                    Integer count,
                                                @RequestParam(defaultValue = "0")
                                                @ApiParam(value = "Default value is 0.")
                                                    Integer page,
                                                @RequestParam(defaultValue = "id")
                                                @ApiParam(value = "Default value is 'id'.")
                                                    String sortBy) {
        return mapToDtos(productService.findAllByPriceBetween(from, to,
                PageRequest.of(page, count, sortParser.parse(sortBy))));
    }

    private Product mapToModel(ProductRequestDto requestDto, Long id) {
        return productRequestDtoMapper.mapToModel(requestDto, id);
    }

    private ProductResponseDto mapToDto(Product product) {
        return productResponseDtoMapper.mapToDto(product);
    }

    private List<ProductResponseDto> mapToDtos(List<Product> products) {
        return products.stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
