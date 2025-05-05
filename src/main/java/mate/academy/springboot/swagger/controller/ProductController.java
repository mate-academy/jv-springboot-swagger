package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.SorterUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<ProductRequestDto, Product, ProductResponseDto> dtoMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product and add to DB")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        return dtoMapper.toDto(productService.create(dtoMapper.toEntity(productRequestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Getting product by id from DB")
    public ProductResponseDto get(@PathVariable Long id) {
        return dtoMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the product by id from DB")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update the product by id from DB")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        return dtoMapper.toDto(productService.update(id, dtoMapper.toEntity(productRequestDto)));
    }

    @GetMapping
    @ApiOperation(value = "Get all products from DB with pagination and sorting availability")
    public List<ProductResponseDto> findAll(
            @ApiParam(value = "items per page")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam(value = "page number")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "sort by certain field")
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = SorterUtil.makeListOfSortConditions(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Getting product by id from DB sorting by price range, id, title")
    public List<ProductResponseDto> findAllByPriceBetween(
            @ApiParam(value = "from price inclusively")
            @RequestParam(defaultValue = "0") BigDecimal from,
            @ApiParam(value = "to price inclusively")
            @RequestParam(defaultValue = "100000000") BigDecimal to,
            @ApiParam(value = "items per page")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam(value = "page number")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "sort by certain field")
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = SorterUtil.makeListOfSortConditions(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
