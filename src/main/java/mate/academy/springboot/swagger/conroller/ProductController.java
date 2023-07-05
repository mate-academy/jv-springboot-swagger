package mate.academy.springboot.swagger.conroller;

import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.PaginationService;
import mate.academy.springboot.swagger.service.ProductService;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<Product, ProductRequestDto, ProductResponseDto> dtoMapper;
    private final PaginationService paginationService;

    @Operation(summary = "Add product to DB")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return dtoMapper.mapToDto(productService.create(dtoMapper.mapToModel(dto)));
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return dtoMapper.mapToDto(productService.getById(id));
    }

    @Operation(summary = "Delete product by id from DB")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @Operation(summary = "Update product in DB")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        Product product = dtoMapper.mapToModel(dto);
        product.setId(id);
        return dtoMapper.mapToDto(productService.update(product));
    }

    @Operation(summary = "Get all Products by price between two values",
            description = "It is possible to use pagination")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam (
                                                                  defaultValue = "10")
                                                          Integer count,
                                                          @RequestParam (
                                                                  defaultValue = "0")
                                                          Integer page,
                                                          @RequestParam (
                                                                  defaultValue = "id")
                                                          String sortBy) {
        final PageRequest pageRequest = paginationService.createPageRequest(count, page, sortBy);
        return productService.findAllByPriceBetween(from,
                        to,
                        paginationService.createPageRequest(count, page, sortBy))
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get all Products",
            description = "It is possible to use pagination")
    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "10") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        return productService.findAll(paginationService.createPageRequest(count, page, sortBy))
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
