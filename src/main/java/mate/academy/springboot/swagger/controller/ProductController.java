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
import mate.academy.springboot.swagger.util.SortingOrders;
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

    public ProductController(ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get one product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.getById(id));
    }

    @PostMapping
    @ApiOperation("Create product and return created product with id")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return responseDtoMapper.mapToDto(
                productService.save(requestDtoMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update information in product with id, and return updated product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Remove product with id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    @ApiOperation("Get list of all products with pagination by pages, and required sorting")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "default value is 20")
                Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is 0")
                Integer page,
            @RequestParam (defaultValue = "title")
                @ApiParam(value = "default value is title")
                String sortBy) {
        Sort sort = Sort.by(SortingOrders.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation("Get list of all products with pagination by pages, required sorting,"
            + " and filtering by price")
    public List<ProductResponseDto> getAllByPrice(
            @RequestParam(defaultValue = "20")
                @ApiParam(value = "default value is 20")
                Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "default value is 0")
                Integer page,
            @RequestParam (defaultValue = "title")
                @ApiParam(value = "default value is title")
                String sortBy,
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to) {
        Sort sort = Sort.by(SortingOrders.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(pageRequest, from, to).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
