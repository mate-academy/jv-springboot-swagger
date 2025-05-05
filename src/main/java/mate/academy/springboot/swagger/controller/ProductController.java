package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortOrderService;
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
    private final ProductMapper mapper;
    private final SortOrderService sortOrderService;

    public ProductController(ProductService productService,
                             ProductMapper mapper, SortOrderService sortOrderService) {
        this.productService = productService;
        this.mapper = mapper;
        this.sortOrderService = sortOrderService;
    }

    @PostMapping
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return mapper.toDto(productService
                .save(mapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(
            @PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        return mapper.toDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get all products",
            notes = "Returns a list of all products sorted by the specified field.")
    public List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "title")
            @ApiParam(value = "The name of the fields to sort the results by. Default is 'title'.")
            String sortBy,
            @RequestParam(defaultValue = "10")
            @ApiParam(value = "The maximum number of items to return per page. Default is 10.")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "The page number to return. Default is 0 (the first page).")
            Integer page) {
        List<Sort.Order> orders = sortOrderService.getSortOrders(sortBy);
        Sort sort = Sort.by(orders);
        Pageable pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get all products with a price between a given range",
            notes = "Returns a list of products whose price is between the specified range.")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "The maximum number of items to return per page. Default is 20.")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "The page number to return. Default is 0 (the first page).")
            Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "The name of the field to sort the results by. Default is 'id'.")
            String sortBy,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "The minimum price of the products to return. Default is 0.")
            BigDecimal from,
            @RequestParam(defaultValue = "99999")
            @ApiParam(value = "The maximum price of the products to return. Default is 99999.")
            BigDecimal to) {
        List<Sort.Order> orders = sortOrderService.getSortOrders(sortBy);
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageable).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
