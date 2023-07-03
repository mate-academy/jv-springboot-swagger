package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.mapper.request.ProductRequestMapper;
import mate.academy.springboot.swagger.mapper.response.ProductResponseMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProductController {
    private final ProductService productService;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    public ProductController(ProductService productService,
                             ProductRequestMapper productRequestMapper,
                             ProductResponseMapper productResponseMapper) {
        this.productService = productService;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    @PostMapping("/")
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        Product product = productService.create(productRequestMapper.fromDto(dto));
        return productResponseMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.get(id);
        return productResponseMapper.toDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        Product product = productService.get(id);
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        productService.update(product);
        return productResponseMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public ProductResponseDto delete(@PathVariable Long id) {
        Product product = productService.get(id);
        productService.delete(product);
        return productResponseMapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "5") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field: sortingFields) {
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
        return productService.findAll(pageRequest).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get a list of products in price range")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam (defaultValue = "5")
                                                      Integer count,
                                                  @RequestParam (defaultValue = "0")
                                                      Integer page,
                                                  @RequestParam (defaultValue = "DESC")
                                                      String sorted) {
        Sort.Order order;
        order = new Sort.Order(Sort.Direction.DESC, "price");
        Sort sort = Sort.by(order);
        Pageable pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productResponseMapper::toDto)
                .collect(Collectors.toList());
    }

}
