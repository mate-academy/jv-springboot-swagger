package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
import mate.academy.springboot.swagger.util.PageRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PageRequestProvider pageRequestProvider;

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ResponseEntity<ProductResponseDto> create(
            @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.save(productMapper.mapToModel(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapToDto(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(productMapper.mapToDto(product));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update all product properties")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable Long id, @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.update(id, productMapper.mapToModel(requestDto));
        return ResponseEntity.ok(productMapper.mapToDto(product));
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public ResponseEntity<List<ProductResponseDto>> getAll(
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer count,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default criteria is id") String sortBy) {
        PageRequest pageRequest = pageRequestProvider.getPageRequest(page, count, sortBy);
        return ResponseEntity.ok(productService.getAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get products lists by price range")
    public ResponseEntity<List<ProductResponseDto>> getAll(
            @RequestParam
            @ApiParam(value = "min price") BigDecimal from,
            @RequestParam
            @ApiParam(value = "max from") BigDecimal to,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is 0") Integer page,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is 20") Integer count,
            @RequestParam(defaultValue = "id")
            @ApiParam(value = "default criteria is id") String sortBy) {
        PageRequest pageRequest = pageRequestProvider.getPageRequest(page, count, sortBy);
        return ResponseEntity.ok(
                productService.getAllByPriceBetween(from, to, pageRequest).stream()
                        .map(productMapper::mapToDto)
                        .collect(Collectors.toList()));
    }
}
