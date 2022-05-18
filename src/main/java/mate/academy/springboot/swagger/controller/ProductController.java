package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "Create is a new product.")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        Product savedProduct = productService.save(product);
        return productMapper.mapToDto(savedProduct);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id.")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return productMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove product by id.")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update existing product by id.")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product productFromDb = productService.findById(id);
        productFromDb.setTitle(productRequestDto.getTitle());
        productFromDb.setPrice(productRequestDto.getPrice());
        return productMapper.mapToDto(productService.save(productFromDb));
    }

    @GetMapping
    @ApiOperation(value = "Get all products.")
    public List<ProductResponseDto> findAll(@RequestParam(required = false)
                                            @ApiParam(value = "Optional parameter.")
                                            BigDecimal priceFrom,
                                            @RequestParam(required = false)
                                            @ApiParam(value = "Optional parameter.")
                                            BigDecimal priceTo,
                                            @RequestParam(defaultValue = "20")
                                            @ApiParam(value = "Default value - 20.")
                                            Integer size,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "Default value - 0.")
                                            Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "Sort by default - by ID.")
                                            String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return priceFrom != null || priceTo != null
                ? productService.findAll(priceFrom, priceTo,
                                pageRequest).stream()
                        .map(productMapper::mapToDto)
                        .collect(Collectors.toList())
                : productService.findAll(pageRequest).stream()
                        .map(productMapper::mapToDto)
                        .collect(Collectors.toList());
    }
}
