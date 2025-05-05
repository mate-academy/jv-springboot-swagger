package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.entity.Product;
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

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    public ProductController(ProductService productService,
                             ProductDtoMapper productDtoMapper) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
    }

    @GetMapping("/inject")
    public String saveCategories() {
        Product lamp = new Product();
        lamp.setPrice(BigDecimal.valueOf(200));
        lamp.setTitle("Lamp for a good light");
        productService.create(lamp);

        Product book = new Product();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(100));
        productService.create(book);

        Product car = new Product();
        car.setTitle("Ferrari");
        car.setPrice(BigDecimal.valueOf(10000.00));
        productService.create(car);

        return "Products were added";
    }

    @ApiOperation(value = "Create new product")
    @PostMapping("/add")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return productDtoMapper.toDto(productService
                .create(productDtoMapper.toModel(productRequestDto)));
    }

    @ApiOperation(value = "Get product by its id")
    @GetMapping("/{productId}")
    public ProductResponseDto getById(@PathVariable Long productId) {
        return productDtoMapper.toDto(productService.getById(productId));
    }

    @ApiOperation(value = "Delete product by its id")
    @DeleteMapping("{productId}")
    public void deleteById(@PathVariable Long productId) {
        productService.delete(productId);
    }

    @ApiOperation(value = "Update product by its id")
    @PutMapping("/{productId}")
    public ProductResponseDto update(@PathVariable Long productId,
                                     @RequestBody ProductRequestDto productRequestDto) {
        return productDtoMapper.toDto(productService.update(productId,
                productDtoMapper.toModel(productRequestDto)));
    }

    @ApiOperation(value = "Get all products between specific range")
    @GetMapping("/price")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam (defaultValue = "20")
                                                                      Integer count,
                                                          @RequestParam (defaultValue = "0")
                                                                      Integer page,
                                                          @RequestParam (defaultValue = "id")
                                                                      String sortBy) {
        PageRequest pageRequest = getPaginationAndSorting(count, page, sortBy);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all products")
    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        PageRequest pageRequest = getPaginationAndSorting(count, page, sortBy);
        return productService.findAll(pageRequest).stream()
                .map(productDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    private static PageRequest getPaginationAndSorting(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingFields = sortBy.split(";");
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(
                            Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }
}
