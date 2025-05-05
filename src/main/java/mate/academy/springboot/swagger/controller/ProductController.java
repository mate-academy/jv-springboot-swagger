package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
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
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             SortUtil sortUtil) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortUtil = sortUtil;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.get(id));
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto save(@RequestBody
                                   @ApiParam(value = "Set product name and price")
                                   ProductRequestDto productRequestDto) {
        Product savedProduct = productService.save(productMapper.toModel(productRequestDto));
        return productMapper.toDto(savedProduct);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update existed product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody
                                     @ApiParam(value = "Update product information")
                                     ProductRequestDto productRequestDto) {
        Product model = productMapper.toModel(productRequestDto);
        model.setId(id);
        return productMapper.toDto(productService.update(model));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    public void delete(@PathVariable
                       @ApiParam(value = "Set product id: ") Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get All products with pagination")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                            @ApiParam(value = "Products per page (default 20)")
                                                Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "Page number") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sort(sortBy));
        return productService.getAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/price")
    @ApiOperation(value = "Find all by price, pagination and sorting")
    public List<ProductResponseDto> findAllByPrice(@RequestParam
                                                   @ApiParam(value = "Set price from: ")
                                                   BigDecimal from,
                                                   @RequestParam
                                                   @ApiParam(value = "Set price to: ")
                                                   BigDecimal to,
                                                   @RequestParam(defaultValue = "20")
                                                   @ApiParam(value = "Products per page:")
                                                   Integer count,
                                                   @RequestParam(defaultValue = "0")
                                                   @ApiParam(value = "Page number")
                                                   Integer page,
                                                   @RequestParam(defaultValue = "id")
                                                   String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sort(sortBy));
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
