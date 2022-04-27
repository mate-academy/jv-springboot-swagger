package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.uti.SortUtil;
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
    private final SortUtil sortUtil;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productMapper.toModel(requestDto);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toDto(productService.getProductById(id));
    }

    @GetMapping
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                            @ApiParam(value = "Default value is `20`")
                                                    Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "Default value is `0`")
                                                    Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "Default value is `id`")
                                                    String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sort(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price/between")
    @ApiOperation(value = "get products by price between")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam(defaultValue = "20")
                                                          @ApiParam(value = "Default value "
                                                                  + "is `20`")
                                                                  Integer count,
                                                          @RequestParam(defaultValue = "0")
                                                          @ApiParam(value = "Default value "
                                                                  + "is `0`")
                                                                  Integer page,
                                                          @RequestParam(defaultValue = "id")
                                                          @ApiParam(value = "Default value "
                                                                  + "is `id`")
                                                                  String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.sort(sortBy));
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
