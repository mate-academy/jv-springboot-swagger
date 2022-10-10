package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortUtil;
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

    @PostMapping
    @ApiOperation("Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.mapToModel(requestDto);
        return responseDtoMapper.mapToDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a product by ID")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update the product by ID and body")
    public ProductResponseDto get(@PathVariable Long id,
                                  @RequestBody ProductRequestDto productRequestDto) {
        Product product = requestDtoMapper.mapToModel(productRequestDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product by ID")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation("Get a sorted list of products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "10") Integer count,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation("Get a sorted list of products ranged by price")
    public List<ProductResponseDto> findAll(@RequestParam BigDecimal fromPrice,
                                            @RequestParam BigDecimal toPrice,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer count,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = SortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        List<Product> productList = productService.findAll(pageRequest);
        List<Product> filteredList = productService
                .filterByPriceBetween(productList, fromPrice, toPrice);
        return filteredList.stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
