package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    public ProductController(ProductService productService, ProductMapper productMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortUtil = sortUtil;
    }

    @ApiOperation(value = "create a new product")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        Product product = productService.save(productMapper.mapToModel(dto));
        return productMapper.mapToDto(product);
    }

    @ApiOperation(value = "get a product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @ApiOperation(value = "delete a product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "update a product by id")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                      @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @ApiOperation(value = "get a list of product by price between given values")
    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam Long from,
                                                  @RequestParam Long to,
                                                  @ApiParam(value = "default value is 20")
                                                  @RequestParam(defaultValue = "20") Integer count,
                                                  @ApiParam(value = "default value is 0")
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @ApiParam(value = "default value is id")
                                                  @RequestParam(defaultValue = "id")
                                                      String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(BigDecimal.valueOf(from),
                        BigDecimal.valueOf(to), pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "get a list of product by price between given values")
    @GetMapping
    public List<ProductResponseDto> getAll(
            @ApiParam(value = "default value is 20")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam(value = "default value is 0")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "default value is id")
            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
