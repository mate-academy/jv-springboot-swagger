package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final SortUtil sortUtil;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> dtoMapper;

    public ProductController(
            ProductService productService,
            SortUtil sortUtil,
            DtoMapper<Product, ProductRequestDto, ProductResponseDto> dtoMapper) {
        this.productService = productService;
        this.sortUtil = sortUtil;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product product = productService.create(dtoMapper.mapToModel(dto));
        return dtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.findById(id);
        return dtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = dtoMapper.mapToModel(dto);
        product.setId(id);
        Product updatedProduct = productService.update(product);
        return dtoMapper.mapToDto(updatedProduct);
    }

    @GetMapping
    @ApiOperation(value = "Get all products with paging and sorting")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "5") Integer count,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = sortUtil.sortData(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get a list of products in price range")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam (defaultValue = "0") BigDecimal from,
            @RequestParam (defaultValue = "0") BigDecimal to,
            @RequestParam (defaultValue = "5") Integer count,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = sortUtil.sortData(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
