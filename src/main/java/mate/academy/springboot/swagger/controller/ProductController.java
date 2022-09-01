package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.impl.ProductMapper;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private static final String MAX_INT = "2147483647";
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return productMapper.toDto(productService.save(productMapper.toModel(requestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update or insert (if not exist) product by id")
    public ProductResponseDto update(@RequestBody ProductRequestDto dto, @PathVariable Long id) {
        Product product = productMapper.toModel(dto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products with pagination")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "20")
            @ApiParam (value = "default value is `20`") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam (value = "default value is `0`") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam (value = "default value is `id`. Can sort by: id, price, "
                    + "title with direction ACS and DESC") String sortBy) {
        Sort sort = Sort.by(sortUtil.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products filtered by price with pagination")
    public List<ProductResponseDto> getAllBetweenPrice(
            @RequestParam BigDecimal fromPrice,
            @RequestParam BigDecimal toPrice,
            @RequestParam (defaultValue = "20")
            @ApiParam (value = "default value is `20`") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam (value = "default value is `0`") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam (value = "default value is `id`. Can sort by: id, price, "
                    + "title with direction ACS and DESC") String sortBy) {
        Sort sort = Sort.by(sortUtil.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page,count, sort);
        return productService.getAllByPrice(fromPrice, toPrice, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
