package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
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
public class ProductController {
    private final SortParser sortUtil;
    private final ProductService service;
    private final ProductMapper mapper;

    public ProductController(SortParser sortUtil,
                             ProductService service,
                             ProductMapper mapper) {
        this.sortUtil = sortUtil;
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "create new product")
    ProductResponseDto create(@RequestBody ProductRequestDto req) {
        return mapper.toProductResponseDto(service.create(mapper.toModel(req)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toProductResponseDto(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto req) {
        return mapper.toProductResponseDto(service.update(mapper.toModel(req), id));
    }

    @GetMapping("/price")
    @ApiOperation(value = "find all products by price range")
    List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                   @RequestParam BigDecimal to,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(defaultValue = "20") Integer size,
                                                   @RequestParam(defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.by(sortUtil.getSortOrders(sortBy)));
        return service.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(mapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation(value = "find all products by sorted list")
    List<ProductResponseDto> findAll(@RequestParam(defaultValue = "20") Integer size,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.by(sortUtil.getSortOrders(sortBy)));
        return service.findAll(pageRequest).stream()
                .map(mapper::toProductResponseDto)
                .collect(Collectors.toList());
    }
}
