package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequest;
import mate.academy.springboot.swagger.dto.ProductResponse;
import mate.academy.springboot.swagger.mapper.ProductDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.RequestSortHandler;
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
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDto productDto;
    private final RequestSortHandler requestSortHandler;

    @PostMapping
    @ApiOperation("create a new product")
    public ProductResponse create(@RequestBody @Valid ProductRequest productRequest) {
        return productDto.toDto(productService.create(productDto.toProduct(productRequest)));
    }

    @PutMapping
    @ApiOperation("update a new product")
    public ProductResponse update(@RequestBody @Valid ProductRequest productRequest) {
        return productDto.toDto(productService.create(productDto.toProduct(productRequest)));
    }

    @GetMapping("/{id}")
    @ApiOperation("get product by id")
    public ProductResponse getById(@PathVariable Long id) {
        return productDto.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/price")
    @ApiOperation("find all product in DB by price with some sort direction")
    public List<ProductResponse> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                      @RequestParam BigDecimal to,
                                                      @RequestParam (defaultValue = "2")
                                                          @ApiParam("default value is 2")
                                                                  Integer count,
                                                      @RequestParam (defaultValue = "0")
                                                                  Integer page,
                                                      @RequestParam (defaultValue = "id")
                                                          @ApiParam("default sort is ASC")
                                                                  String sortBy) {
        Sort sort = Sort.by(requestSortHandler.getSortProductsByDirection(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ApiOperation("find all product in DB with some sort direction")
    public List<ProductResponse> getAll(@ApiParam("default value is 2")
                                            @RequestParam (defaultValue = "2") Integer count,
                                @RequestParam (defaultValue = "0") Integer page,
                                @RequestParam (defaultValue = "id")
                                            @ApiParam("default sort is ASC") String sortBy) {
        Sort sort = Sort.by(requestSortHandler.getSortProductsByDirection(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productDto::toDto)
                .collect(Collectors.toList());
    }
}
