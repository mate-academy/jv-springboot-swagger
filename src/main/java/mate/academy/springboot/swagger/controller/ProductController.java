package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.Sorting;
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

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper mapper;
    private final Sorting sorting;

    public ProductController(ProductService productService,
                             ProductMapper mapper,
                             Sorting sorting) {
        this.productService = productService;
        this.mapper = mapper;
        this.sorting = sorting;
    }

    @PostMapping
    @ApiOperation(value = "create new product")
    public ProductResponseDto create(ProductRequestDto requestDto) {
        return mapper.responseDto(productService.save(mapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.responseDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "upgrade product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     ProductRequestDto requestDto) {
        Product product = mapper.toModel(requestDto);
        product.setId(id);
        return mapper.responseDto(productService.update(product));
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "get all products with pagination and sort")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @ApiParam (value = "default value = 20")
                                            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sorting.toOrder(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(mapper::responseDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/findByPrice")
    @ApiOperation(value = "get all products by price with pagination and sort")
    public List<ProductResponseDto>
            findAllByPriceBetween(@RequestParam BigDecimal from,
                                  @RequestParam BigDecimal to,
                                  @RequestParam (defaultValue = "20") Integer count,
                                  @ApiParam (value = "default value = 20")
                                  @RequestParam (defaultValue = "0") Integer page,
                                  @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sorting.toOrder(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(mapper::responseDto)
                .collect(Collectors.toList());
    }
}
