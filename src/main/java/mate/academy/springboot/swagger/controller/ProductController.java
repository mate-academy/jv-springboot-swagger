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
import mate.academy.springboot.swagger.util.ProductSortUtilParser;
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
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> dtoMapper;

    @PostMapping
    @ApiOperation(value = "Add new products")
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        return dtoMapper.toDto(productService.add(dtoMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        return dtoMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product from database by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product in database by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestParam ProductRequestDto requestDto) {
        Product product = dtoMapper.toModel(requestDto);
        product.setId(id);
        return dtoMapper.toDto(productService.update(product));
    }

    @GetMapping("/find-all")
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20")
                                                @ApiParam(value = "default value is 20")
                                                Integer count,
                                            @RequestParam(defaultValue = "0")
                                                @ApiParam(value = "default value is 0")
                                                Integer page,
                                            @RequestParam(defaultValue = "id")
                                                @ApiParam(value = "default value is id")
                                                String sortBy) {
        Sort sort = Sort.by(ProductSortUtilParser.toSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all product between price")
    public List<ProductResponseDto> findAllByPrice(@RequestParam BigDecimal from,
                                                   @RequestParam BigDecimal to,
                                                   @RequestParam(defaultValue = "20")
                                                        @ApiParam(value = "default value is 20")
                                                        Integer count,
                                                   @RequestParam(defaultValue = "0")
                                                        @ApiParam(value = "default value is 0")
                                                        Integer page,
                                                   @RequestParam(defaultValue = "id")
                                                        @ApiParam(value = "default value is id")
                                                        String sortBy) {
        Sort sort = Sort.by(ProductSortUtilParser.toSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPrice(from, to, pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
