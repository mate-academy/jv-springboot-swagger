package mate.academy.springboot.swagger.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @Autowired
    public ProductController(ProductService service,
                             ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(service.create(mapper.toModel(dto)));
    }

    @GetMapping("/get")
    public ProductResponseDto get(@RequestParam Long id) {
        return mapper.toDto(service.get(id));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        service.delete(id);
    }

    @PutMapping("/update")
    public ProductResponseDto update(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(service.update(mapper.toModel(dto)));
    }

    @GetMapping
    @Operation( summary = "Get All Products | With Parameters")
    @Parameter(name = "page",
            allowEmptyValue = true,
            description = "Specify the page | Should be > 0")
    @Parameter(name = "count",
            allowEmptyValue = true,
            description = "Specify the amount of products | Should be > 0")
    @Parameter(name = "sortBy",
            allowEmptyValue = true,
            description = "Specify the field for sorting - id, title, price "
                    + "and sorting direction | Example title: ASC or price:DESC")
    @Parameter(name = "priceFrom",
            allowEmptyValue = true,
            description = "Specify price from")
    @Parameter(name = "priceTo",
            allowEmptyValue = true,
            description = "Specify price to")
    private List<ProductResponseDto> getAll(@RequestParam Map<String, String> params) {
        return service.getAll(params)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
