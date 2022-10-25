package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductServiceImpl;
import mate.academy.springboot.swagger.service.SortUtil;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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
    private final ProductServiceImpl productService;
    private final ProductMapper mapper;

    public ProductController(ProductServiceImpl productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto saveProduct(@RequestBody
                                              @ApiParam(value = "product which you want add to DB")
                                              ProductRequestDto requestDto) {
        Product product = productService.save(mapper.fromDto(requestDto));
        return mapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return mapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestParam ProductRequestDto requestDto) {
        Product product = mapper.fromDto(requestDto);
        product.setId(id);
        return mapper.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "get list products "
            + "with using pagination and soring by params")
    public List<ProductResponseDto>
            getAll(@RequestParam(defaultValue = "20")
                   @ApiParam(value = "count by page is 20 by default")
                   Integer count,
                   @RequestParam(defaultValue = "0")
                   @ApiParam(value = "first page start with 0 by default")
                   Integer page,
                   @RequestParam(defaultValue = "id")
                   @ApiParam(value = "sorting by id by default")
                   String sortBy) {

        Sort sort = Sort.by(SortUtil.sort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get list products between price "
            + "with using pagination and soring by params")
    public List<ProductResponseDto>
            getAllByPrice(@RequestParam @ApiParam(value = "from price")
                          BigDecimal from,
                          @RequestParam @ApiParam(value = "to price")
                          BigDecimal to,
                          @RequestParam(defaultValue = "20")
                          @ApiParam(value = "count by page is 20 by default")
                          Integer count,
                          @RequestParam(defaultValue = "0")
                          @ApiParam(value = "first page start with 0 by default")
                          Integer page,
                          @RequestParam(defaultValue = "id")
                          @ApiParam(value = "sorting by id by default")
                          String sortBy) {

        Sort sort = Sort.by(SortUtil.sort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllForPriceBetween(from, to, pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
