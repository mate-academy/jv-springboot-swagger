package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductDtoMapper mapper;
    private final ProductService service;
    private final SortUtil sortUtil;

    @Autowired
    public ProductController(ProductDtoMapper mapper, ProductService service, SortUtil sortUtil) {
        this.mapper = mapper;
        this.service = service;
        this.sortUtil = sortUtil;
    }

    @PostMapping
    @ApiOperation(value = "Save a new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        return mapper.modelToDto(service.save(mapper.dtoToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.modelToDto(service.getById(id));
    }

    @DeleteMapping
    @ApiOperation(value = "Delete a product by id")
    public void deleteById(@RequestParam Long id) {
        service.deleteById(id);
    }

    @PutMapping
    @ApiOperation(value = "Update a product")
    public void update(@RequestBody Product product) {
        service.update(product);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Get all products (page size, page number, sortBy parameters)")
    public List<ProductResponseDto> getAll(@ApiParam (defaultValue = "3") Integer count,
                                           @ApiParam (defaultValue = "0") Integer page,
                                           @ApiParam (defaultValue = "id") String sortBy) {
        Sort sort = sortUtil.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return service.getAll(pageRequest).stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByPriceBetween")
    public List<ProductResponseDto> getAllByPriceBetween(
                @ApiParam (defaultValue = "3") Integer count,
                @ApiParam (defaultValue = "0") Integer page,
                @ApiParam(defaultValue = "id") String sortBy,
                @ApiParam BigDecimal from,
                @ApiParam BigDecimal to) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.parse(sortBy));
        return service.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }
}
