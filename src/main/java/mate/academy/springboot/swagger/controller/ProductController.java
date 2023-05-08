package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
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
    @ApiOperation(value = "Creates a new product",
            httpMethod = "POST",
            response = ProductResponseDto.class)
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(service.create(mapper.toModel(dto)));
    }

    @GetMapping("/get")
    @ApiOperation(value = "Returns the product by id",
            httpMethod = "GET",
            response = ProductResponseDto.class)
    public ProductResponseDto get(@RequestParam Long id) {
        return mapper.toDto(service.get(id));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Deletes the product by id",
            httpMethod = "DELETE")
    public void delete(@RequestParam Long id) {
        service.delete(id);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Updates the product by id",
            httpMethod = "UPDATE",
            response = ProductResponseDto.class)
    public ProductResponseDto update(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(service.update(mapper.toModel(dto)));
    }

    @GetMapping
    @ApiOperation(value = "Returns all products",
            httpMethod = "GET",
            response = ProductResponseDto.class,
            responseContainer = "List")
    private List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "1")
            @Parameter(description = "Default Value = 1") String page,
            @RequestParam(defaultValue = "4")
            @Parameter(description = "Default Value = 4") String count,
            @RequestParam(defaultValue = "title:ASC")
            @Parameter(description = "Default Value = title:ASC") String sortBy,
            @RequestParam(required = false) String priceFrom,
            @RequestParam(required = false) String priceTo
    ) {
        return service.getAll(page, count, sortBy, priceFrom, priceTo)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
