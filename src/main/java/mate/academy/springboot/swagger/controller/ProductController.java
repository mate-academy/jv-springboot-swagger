package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductDtoMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductDtoMapper productDtoMapper;
    private final ProductService productService;

    public ProductController(ProductDtoMapper productDtoMapper,
                             ProductService productService) {
        this.productDtoMapper = productDtoMapper;
        this.productService = productService;
    }

    @PostMapping
    private ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productDtoMapper.toProduct(productRequestDto);
        product = productService.save(product);
        return productDtoMapper.toResponseDto(product);
    }

    @GetMapping
    private ProductResponseDto getById(@RequestParam Long id) {
        Product product = productService.getById(id);
        return productDtoMapper.toResponseDto(product);
    }

    @DeleteMapping
    private void deleteById(@RequestParam Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    private void update(@RequestBody Product product) {
        productService.update(product);
    }

    @GetMapping("/getAll")
    private List<ProductResponseDto> getAll(@RequestParam (defaultValue = "3") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy,
                                            @RequestParam BigDecimal from,
                                            @RequestParam BigDecimal to) {
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
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByPriceBetween")
    private List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to) {
        return productService.getAllByPriceBetween(from, to).stream()
                .map(productDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}