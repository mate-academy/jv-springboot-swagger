package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.mapper.DtoMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.RequestProvider;
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
    private final DtoMapper<ProductRequestDto, Product, ProductResponseDto> dtoMapper;
    private final RequestProvider requestProvider;

    @PostMapping
    @ApiOperation("create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return dtoMapper.mapToDto(
                productService.save(
                        dtoMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation("return product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return dtoMapper.mapToDto(
                productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        return dtoMapper.mapToDto(
                productService.update(id,
                        dtoMapper.mapToModel(requestDto)));
    }

    @GetMapping
    @ApiOperation("return all exists products with pagination and sort possibilities")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "20")
                @ApiParam("the number of elements on the page. Default = 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam("page number. Default =  0 (first)") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam("by which parameter to sort in which order. "
                        + "Default = id:DESC") String sortBy) {
        return productService.getAll(requestProvider.formPageRequest(count, page, sortBy))
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between")
    @ApiOperation("return products in price range with pagination and sort possibilities")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam @ApiParam("the lowest price value") BigDecimal from,
            @RequestParam @ApiParam("the highest price value") BigDecimal to,
            @RequestParam (defaultValue = "20")
                @ApiParam("the number of elements on the page. Default = 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam("page number. Default =  0 (first)") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam("by which parameter to sort in which order. "
                        + "Default = id:DESC") String sortBy) {
        return productService.getAllByPriceBetween(
                requestProvider.formPageRequest(count, page, sortBy), from, to)
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
