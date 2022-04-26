package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.mapper.DtoRequestMapper;
import mate.academy.springboot.swagger.mapper.DtoResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final DtoRequestMapper<ProductRequestDto, Product> dtoRequestMapper;
    private final DtoResponseMapper<ProductResponseDto, Product> dtoResponseMapper;

    @PostMapping
    public ProductResponseDto create(@RequestBody @Valid ProductRequestDto dto) {
        Product product = productService.save(dtoRequestMapper.toEntity(dto));
        return dtoResponseMapper.toDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return dtoResponseMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid ProductRequestDto dto) {
        Product product = dtoRequestMapper.toEntity(dto);
        product.setId(id);
        Product productUpdated = productService.update(product);
        return dtoResponseMapper.toDto(productUpdated);
    }

    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "30") Integer count,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        return productService.findAll(page, count, sortBy)
                .stream()
                .map(dtoResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    public List<ProductResponseDto> findAllByPriceBetween(
                              @RequestParam BigDecimal from,
                              @RequestParam BigDecimal to,
                              @RequestParam (defaultValue = "0") Integer page,
                              @RequestParam (defaultValue = "30") Integer count,
                              @RequestParam (defaultValue = "price") String sortBy) {
        return productService.findAllByPriceBetween(from, to, page, count, sortBy)
                .stream()
                .map(dtoResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}
