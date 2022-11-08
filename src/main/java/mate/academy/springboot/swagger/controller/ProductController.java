package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.PageParserUtil;
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

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return mapper.toDto(service.create(mapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toDto(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = mapper.toModel(requestDto);
        product.setId(id);
        return mapper.toDto(service.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get list of all products with possibility to pagination and sorting")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "10") Integer count,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageParserUtil.parse(count, page, sortBy);
        return service.findAll(pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get list of all products by price between two input values with "
            + "possibility to pagination and sorting")
    public List<ProductResponseDto> findAllByPriceBetween(@RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to,
                                                          @RequestParam(defaultValue = "3")
                                                          Integer count,
                                                          @RequestParam(defaultValue = "0")
                                                          Integer page,
                                                          @RequestParam(defaultValue = "id")
                                                          String sortBy) {
        PageRequest pageRequest = PageParserUtil.parse(count, page, sortBy);
        return service.findAllByPriceBetween(pageRequest, from, to)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
