package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.Parser;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper mapper;
    private final Parser parser;

    @PostMapping
    @ApiOperation(value = "Add a product to DB")
    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        return mapper.mapToDto(productService.add(mapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id from DB")
    public ProductResponseDto get(@PathVariable Long id) {
        return mapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id from DB")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product in DB")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        Product product = mapper.mapToModel(dto);
        product.setId(id);
        return mapper.mapToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Returns all products from DB with pagination and sorting")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = parser.getOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAll(pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Returns the products from the database by the "
            + "specified price parameters with pagination and sorting")
    public List<ProductResponseDto> findAllByPriceBetween(
                        @RequestParam(defaultValue = "0") BigDecimal from,
                        @RequestParam(defaultValue = "0") BigDecimal to,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "0") Integer page,
                        @RequestParam(defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = parser.getOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
