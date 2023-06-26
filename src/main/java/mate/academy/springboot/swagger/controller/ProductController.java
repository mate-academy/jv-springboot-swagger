package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private SortUtil sortUtil;
    private DtoMapper<Product,
            ProductResponseDto,
            ProductRequestDto> mapper;

    @GetMapping
    @ApiOperation(value = "Get all products")
    List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "number of items on page. default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "requested page") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "sorting parameters given in form field:order") String sortBy) {
        Sort sort = Sort.by(sortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all products in within given price")
    List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = "number of items on page. default value is 20") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = "requested page") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = "sorting parameters given in form field:order") String sortBy,
            @RequestParam
                @ApiParam(value = "lower border of given price") BigDecimal from,
            @RequestParam
                @ApiParam(value = "upper border of given price") BigDecimal to) {
        Sort sort = Sort.by(sortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    ProductResponseDto findById(@PathVariable
                                    @ApiParam(value = "Id of item") Long id) {
        return mapper.toDto(productService.find(id));
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    ProductResponseDto save(@RequestBody(description =
            "required product to create in DB") ProductRequestDto dto) {
        Product product = mapper.toModel(dto);
        return mapper.toDto(productService.save(product));
    }

    @PutMapping
    @ApiOperation(value = "Update existent product")
    void update(@RequestBody(description =
            "required product to create in DB") ProductRequestDto dto) {
        productService.save(mapper.toModel(dto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    void delete(@PathVariable
                    @ApiParam(value = "Id of item") Long id) {
        productService.delete(id);
    }
}
