package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
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
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final SortUtil sortUtil;

    public ProductController(ProductMapper productMapper,
                             ProductService productService,
                             SortUtil sortUtil) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.sortUtil = sortUtil;
    }

    @ApiOperation(value = "Add new product to DB")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        return productMapper.toDto(productService.save(productMapper
                .toModel(productRequestDto)));
    }

    @ApiOperation(value = "Get product by id")
    @GetMapping("/id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @ApiOperation(value = "Delete product by id")
    @DeleteMapping
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "Update product by id")
    @PutMapping
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @ApiOperation(value = "Get all sorted and pagination products")
    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                            @ApiParam(value = "Default value is 20")
                                            Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "Default value is 0")
                                            Integer page,
                                            @RequestParam(defaultValue = "title")
                                            @ApiParam(value = "Default value is title")
                                            String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all products between from and to price. Sorted and pagination")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam(defaultValue = "20")
                                                          @ApiParam(value = "Default value is 20")
                                                          Integer count,
                                                          @RequestParam(defaultValue = "0")
                                                          @ApiParam(value = "Default value is 0")
                                                          Integer page,
                                                          @RequestParam(defaultValue = "title")
                                                          @ApiParam(value = "Default value "
                                                                  + "is title")
                                                          String sortBy) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findProductBetweenPrice(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
