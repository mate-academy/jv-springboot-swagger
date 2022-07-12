package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.PagingAndSortingService;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
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
    private final PagingAndSortingService pagingAndSortingService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;

    public ProductController(ProductService productService,
                             PagingAndSortingService pagingAndSortingService,
                             RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper) {
        this.productService = productService;
        this.pagingAndSortingService = pagingAndSortingService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product product = productService.save(requestDtoMapper.mapToModel(dto));
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.get(id);
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all products with paging & sorting features")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "10") @ApiParam(value =
                    "Product count (default = 10)") Integer count,
            @RequestParam (defaultValue = "0") @ApiParam(value =
                    "Page number (default = 0)") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value =
                    "Order by (default = DESC)") String sortBy) {
        PageRequest pageRequest = pagingAndSortingService.paginateAndSort(count, page, sortBy);
        return productService.findAll(pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price-between")
    @ApiOperation(value = "Get all products with price parameters and paging & sorting features")
    public List<ProductResponseDto> getAllWithPriceBetween(
            @RequestParam @ApiParam(value = "Price from") BigDecimal from,
            @RequestParam @ApiParam(value = "Price to") BigDecimal to,
            @RequestParam (defaultValue = "10") @ApiParam(value =
                    "Product count (default = 10)") Integer count,
            @RequestParam (defaultValue = "0") @ApiParam(value =
                    "Page number (default = 0)") Integer page,
            @RequestParam (defaultValue = "id") @ApiParam(value =
                    "Order by (default = DESC)") String sortBy) {
        PageRequest pageRequest = pagingAndSortingService.paginateAndSort(count, page, sortBy);
        return productService.findAllByPriceBetweenWithPagingAndSorting(from, to, pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = requestDtoMapper.mapToModel(dto);
        product.setId(id);
        Product newProduct = productService.save(product);
        return responseDtoMapper.mapToDto(newProduct);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
