package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortParser;
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
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "REST-full endpoints  related to product")
public class ProductController {
    private final ProductMapper mapper;
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Get product from database by id")
    public ProductResponseDto create(Product product) {
        return mapper.toProductResponseDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Create product in database")
    public ProductResponseDto findById(@PathVariable Long id) {
        return mapper.toProductResponseDto(productService.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update product in database")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = mapper.toModel(requestDto);
        return mapper.toProductResponseDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete product in database")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/findAll")
    @Operation(summary = "find all products with pagination")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "15") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.findAll(pageRequest).stream()
                .map(mapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/findAllByPriceBetween")
    @Operation(summary = "find all products with pagination, sorting and price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "15") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = SortParser.generateOrders(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(orders));
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(mapper::toProductResponseDto)
                .collect(Collectors.toList());
    }
}
