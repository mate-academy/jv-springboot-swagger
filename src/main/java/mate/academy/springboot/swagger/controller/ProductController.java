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
import mate.academy.springboot.swagger.util.PaginationUtil;
import org.springframework.data.domain.Pageable;
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
    private final ProductService productService;
    private final ProductMapper mapper;
    private final PaginationUtil paginationUtil;

    public ProductController(
            ProductService productService,
            ProductMapper mapper,
            PaginationUtil paginationUtil
    ) {
        this.productService = productService;
        this.mapper = mapper;
        this.paginationUtil = paginationUtil;
    }

    @ApiOperation("CRUD: Create product")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto receiveDto) {
        return mapper.toDto(productService.createOrUpdate(mapper.toModel(receiveDto)));
    }

    @ApiOperation("CRUD: Read product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return mapper.toDto(productService.getById(id));
    }

    @ApiOperation("CRUD: Update product by id")
    @PutMapping("/{id}")
    public ProductResponseDto update(
            @PathVariable Long id,
            @RequestBody ProductRequestDto receiveDto
    ) {
        Product product = mapper.toModel(receiveDto);
        product.setId(id);
        return mapper.toDto(productService.createOrUpdate(product));
    }

    @ApiOperation("CRUD: Remove product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ApiOperation("Find and get all products. "
            + "Parameters for pagination are optional (see default values)")
    @GetMapping
    public List<ProductResponseDto> findAll(
            @ApiParam("Select page for pagination from 0 to any (others will be empty). "
                    + "Default value is: `0`")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam("Select items count per page or pagination. Default value is: `20`")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam("Select sorting with syntax like: "
                    + "`itemSortBy:<direction>;itemSortBy:<direction>;...`"
                    + "(sorting method aka direction is: 1. optional=DESC; 2. case insensitive)."
                    + "Example: `id;price:ASC` or `id` or `title:DESC`"
                    + "Default value is: `id`")
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return productService.findAll(paginationUtil.getPagination(page, count, sortBy))
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Find and get all products by price between specified values with pagination. "
            + "Parameters for pagination are optional (see default values)")
    @GetMapping("/byPrice")
    public List<ProductResponseDto> findByPriceBetween(
            @RequestParam BigDecimal priceFrom,
            @RequestParam BigDecimal priceTo,
            @ApiParam("Select page for pagination from 1 to any (others will be empty). "
                    + "Default value is: `1`")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam("Select items count per page or pagination. Default value is: `20`")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam("Select sorting with syntax like: "
                    + "`itemSortBy:<direction>;itemSortBy:<direction>;...`"
                    + "(sorting method aka direction is: 1. optional=DESC; 2. case insensitive)."
                    + "Example: `id;price:ASC` or `id` or `title:DESC`"
                    + "Default value is: `id`")
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pagination = paginationUtil.getPagination(page, count, sortBy);
        return productService.findAllBetweenPrice(priceFrom, priceTo, pagination)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
