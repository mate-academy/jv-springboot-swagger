package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.util.SortingPageUtil;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper dtoMapper;

    @PostMapping
    @ApiOperation(value = "Save new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = dtoMapper.toModel(requestDto);
        return dtoMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return dtoMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = dtoMapper.toModel(requestDto);
        product.setId(id);
        return dtoMapper.toDto(productService.save(product));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products with prices from - to")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam
                                                             @ApiParam(value = "Smaller price")
                                                             BigDecimal from,
                                                         @RequestParam
                                                         @ApiParam(value = "Bigger price")
                                                             BigDecimal to,
                                                         @RequestParam(defaultValue = "20")
                                                             @ApiParam(value = "Elements quantity"
                                                                     + " on page. Default 20")
                                                             Integer count,
                                                         @RequestParam(defaultValue = "0")
                                                             @ApiParam(value = "Page number."
                                                                     + " Default 0")
                                                             Integer page,
                                                         @RequestParam(defaultValue = "id")
                                                             @ApiParam(value = "Sorting by field."
                                                                     + " Default field ID")
                                                             String sortBy) {
        Sort sort = SortingPageUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "Find all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                                @ApiParam(value = "Elements quantity on page."
                                                        + " Default 20")
                                                Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "Page number. Default 0")
                                                Integer page,
                                            @RequestParam(defaultValue = "id")
                                                @ApiParam(value = "Sorting by field."
                                                        + " Default field ID")
                                                String sortBy) {
        Sort sort = SortingPageUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
