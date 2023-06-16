package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
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
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = " Add new product to DB")
    public ProductResponseDto create(@RequestBody(description = "Product to add", required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
                                         @Valid ProductRequestDto requestDto) {
        Product product = productService.create(productMapper.mapToModel(requestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/inject")
    @ApiOperation(value = " inject 100 phones")
    public void inject() {
        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setTitle("phone " + i);
            product.setPrice(BigDecimal.valueOf(i + 300));
            productService.create(product);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = " Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = " Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody(description = "Product to update",
                                             required = true,
                                             content = @Content(
                                             schema = @Schema(
                                                     implementation = ProductRequestDto.class)))
                                     @Valid ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = " Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = " Get all products with pagination and sorting")
    public List<ProductResponseDto> getAll(
            @RequestParam (defaultValue = "0")
            @ApiParam(value = " enter the page number, default 0") Integer page,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = " enter the number of products per page, default 20") Integer count,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = " use to sorting, format 'column:ASC;column2:DESC', default id:DESC")
            String sortBy) {
        Sort sort = productService.parseSortParam(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price-between")
    @ApiOperation(value = " Get products by price range with pagination")
    public List<ProductResponseDto> getAllByPriceBetween(
            @RequestParam @ApiParam(value = " enter lowest price") BigDecimal from,
            @RequestParam @ApiParam(value = " enter highest price") BigDecimal to,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = " enter the page number, default 0") Integer page,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = " enter the number of products per page, default 20") Integer count,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = " use to sorting, format 'column:ORDER', default id:DESC")
            String sortBy) {
        Sort sort = productService.parseSortParam(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
