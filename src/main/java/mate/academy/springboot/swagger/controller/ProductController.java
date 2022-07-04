package mate.academy.springboot.swagger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import io.swagger.annotations.ApiOperation;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.exception.CustomGlobalExceptionHandler;
import mate.academy.springboot.swagger.exception.DataProcessException;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProductController extends CustomGlobalExceptionHandler {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductMapper productMapper,
                             ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new product.")
    public ProductResponseDto create(ProductRequestDto requestDto) {
        Product product = productService.create(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id.")
    public ProductResponseDto getById(@PathVariable Long id) throws DataProcessException {
        return productMapper.toDto(productService.find(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id.")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping()
    @ApiOperation(value = "Update a product.")
    public ProductResponseDto update(ProductRequestDto requestDto) {
        Product product = productService.update(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @GetMapping()
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20") Integer count,
                                           @RequestParam (defaultValue = "1") Integer page,
                                           @RequestParam (defaultValue = "id") String sortBy,
                                           @RequestParam Map<String, String> params) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.fromString(fieldsAndDirections[1]),
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
        Pageable pageable = PageRequest.of(count, page, sort);
        return productService.getAll(params, pageable).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
