package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
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
    private final ProductMapper mapper;
    private final ProductService service;

    public ProductController(ProductMapper mapper, ProductService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Get all products. Sort by title or/and by price")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20")
                                           @ApiParam(value = "Default value is 20")
                                           Integer count,
                                           @RequestParam (defaultValue = "0")
                                           @ApiParam(value = "DefaultValue is 0")
                                           Integer page,
                                           @RequestParam (defaultValue = "id")
                                           @ApiParam(value = "Default value is id")
                                           String sortBy) {
        Sort sort = Sort.by(SortingService.parseSortingOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return service.findAll(pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by ID")
    public ProductResponseDto get(@PathVariable Long id) {
        return mapper.toDto(service.get(id));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products with price between two values. "
            + "Sort by title or/and by price")
    public List<ProductResponseDto> getAllBetween(@RequestParam BigDecimal fromPrice,
                                                  @RequestParam BigDecimal toPrice,
                                                  @RequestParam (defaultValue = "20")
                                                  @ApiParam(value = "Default value is 20")
                                                  Integer count,
                                                  @RequestParam (defaultValue = "0")
                                                  @ApiParam(value = "DefaultValue is 0")
                                                  Integer page,
                                                  @RequestParam (defaultValue = "id")
                                                  @ApiParam(value = "Default value is id")
                                                  String sortBy) {
        Sort sort = Sort.by(SortingService.parseSortingOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return service.findAll(fromPrice, toPrice, pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = service.save(mapper.toModel(productRequestDto));
        return mapper.toDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by ID")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        return mapper.toDto(service.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by ID")
    public void delete(@PathVariable Long id) {
        service.remove(id);
    }

}
