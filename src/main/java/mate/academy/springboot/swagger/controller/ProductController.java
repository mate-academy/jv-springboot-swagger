package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.util.PaginationSortingCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final PaginationSortingCondition condition;

    @Autowired
    public ProductController(ProductMapper productMapper,
                             ProductService productService,
                             PaginationSortingCondition condition) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.condition = condition;
    }

    @PostMapping
    @ApiOperation(value = "Create and save new product to DB!")
    ProductResponseDto save(@RequestBody
                            @ApiParam(value = "Please insert parameters of product")
                                    ProductRequestDto requestDto) {
        return productMapper.toResponseDto(productService.save(productMapper.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get one product by id!")
    ProductResponseDto getById(@PathVariable
                               @ApiParam(value = "Insert id of product, that you need!") Long id) {
        return productMapper.toResponseDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete one product by id!")
    void deleteById(@PathVariable
                    @ApiParam(value = "Insert id of product, what you that to delete!") Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update one product by id!")
    ProductResponseDto update(@PathVariable
                              @ApiParam(value = "Insert id of product, that you want to update!")
                                      Long id,
                              @RequestBody
                              @ApiParam(value = "Please insert new parameters of product, "
                                      + "that you want update!") ProductRequestDto requestDto) {
        Product product = productService.getById(id);
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());

        return productMapper.toResponseDto(productService.update(product));
    }

    @GetMapping("/sorting")
    @ApiOperation(value = "Get all product with sorting!")
    List<ProductResponseDto> getAllWithSort(@RequestParam(defaultValue = "5")
                                            @ApiParam(value = "Value of number products on page! "
                                                    + "Default value is 5!") Integer count,
                                            @RequestParam(defaultValue = "0")
                                            @ApiParam(value = "Value of number page! "
                                                    + "Default value is 0!") Integer page,
                                            @RequestParam(defaultValue = "id")
                                            @ApiParam(value = "Insert param for sorting products! "
                                                    + "Default value is id of products!")
                                                    String sortBy) {
        PageRequest pageRequest = condition.getConditionPaginationSorting(page, count, sortBy);
        return productService.getAllWithSort(pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between/sorting")
    @ApiOperation(value = "Get all product with sorting and where price between!")
    List<ProductResponseDto> getAllWherePriceBetweenAndSorting(
            @RequestParam(defaultValue = "2")
            @ApiParam(value = "Value of number products on page! "
                    + "Default value is 2!") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "Value of number page! "
                    + "Default value is 0!") Integer page,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "Insert param for sorting products! "
                    + "Default value is id of products!") String sortBy,
            @RequestParam
            @ApiParam(value = "Please insert first value of price!") Double from,
            @RequestParam
            @ApiParam(value = "Please insert second value of price!") Double to) {
        PageRequest pageRequest = condition.getConditionPaginationSorting(page, count, sortBy);
        return productService.getAllWherePriceBetweenWithSort(from, to, pageRequest)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
