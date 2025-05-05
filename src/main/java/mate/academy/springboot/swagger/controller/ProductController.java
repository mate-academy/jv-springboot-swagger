package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.impl.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @PostMapping
    @ApiOperation("add new product")
    public ProductResponseDto add(@RequestBody @Valid ProductRequestDto dto) {
        Product product = service.add(mapper.mapToModel(dto));
        return mapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation("get product by ID")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.mapToDto(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete product by ID")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("update product by ID")
    public ProductResponseDto update(@PathVariable Long id,
            @RequestBody @Valid ProductRequestDto dto) {
        return mapper.mapToDto(service.update(mapper.mapToModel(dto), id));
    }

    @GetMapping
    @ApiOperation("get all products with pagination and ability to sort by title/price "
            + "(in ASC/DESC order)")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
            @ApiParam(value = "20 products per page by default") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "0 page by default") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam("sort by id by default") String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return service.getAll(pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation("get all products where price is between parameters(from/to) with pagination "
            + "and ability to sort by title/price (in ASC/DESC order)")
    public List<ProductResponseDto> getAll(@RequestParam
            @ApiParam(value = "price from") BigDecimal from,
            @RequestParam
            @ApiParam(value = "price to") BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "20 products per page by default") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "0 page by default") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam("sort by id by default") String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return service.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
