package mate.academy.springboot.swagger.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.mapper.RequestMapper;
import mate.academy.springboot.swagger.model.dto.mapper.ResponseMapper;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.resource.helper.SortHelper;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ProductResource {
    private final ProductService productService;
    private final RequestMapper<ProductRequestDto, Product> requestMapper;
    private final ResponseMapper<ProductResponseDto, Product> responseMapper;

    @PostMapping
    @ApiOperation("save new product")
    public ResponseEntity<ProductResponseDto> save(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(requestMapper.toEntity(requestDto));
        return new ResponseEntity<>(responseMapper.toDto(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("find a product by id")
    public ResponseEntity<ProductResponseDto> findById(
            @ApiParam(value = "id to find a product by", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(responseMapper.toDto(productService.findById(id)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete a product by id")
    public ResponseEntity<Void> delete(
            @ApiParam(value = "id to delete a product by", required = true)
            @PathVariable Long id
    ) {
        productService.delete(productService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ApiOperation("update a product by id")
    public ResponseEntity<ProductResponseDto> update(
            @ApiParam(value = "id to update a product by", required = true)
            @PathVariable Long id,

            @RequestBody ProductRequestDto requestDto
    ) {
        Product product = requestMapper.toEntity(requestDto);
        product.setId(id);
        return ResponseEntity.ok(responseMapper.toDto(productService.update(product)));
    }

    @GetMapping
    @ApiOperation("find all products")
    public ResponseEntity<List<ProductResponseDto>> findAll(
            @ApiParam(value = "page to show", defaultValue = "0")
            @RequestParam(defaultValue = "0") Integer page,

            @ApiParam(value = "number of product on a page", defaultValue = "20")
            @RequestParam(defaultValue = "20") Integer size,

            @ApiParam(value = "sorting options", defaultValue = "id", example = "price:DESC")
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy
    ) {
        Page<Product> products = productService.findAll(PageRequest.of(
                page, size, SortHelper.parseSortOptions(sortBy)
        ));
        return ResponseEntity.ok(mapMany(products));
    }

    private List<ProductResponseDto> mapMany(Streamable<Product> products) {
        return products.stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "find all products by price", response = List.class)
    public ResponseEntity<List<ProductResponseDto>> findAllByPriceBetween(
            @ApiParam(value = "'from' price value", required = true)
            @RequestParam BigDecimal from,

            @ApiParam(value = "'to' price value", required = true)
            @RequestParam BigDecimal to,

            @ApiParam(value = "page to show", defaultValue = "0")
            @RequestParam(defaultValue = "0") Integer page,

            @ApiParam(value = "number of product on a page", defaultValue = "20")
            @RequestParam(defaultValue = "20") Integer size,

            @ApiParam(value = "sorting options", defaultValue = "id", example = "price:DESC")
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy
    ) {
        Page<Product> products = productService.findAllByPriceBetween(from, to, PageRequest.of(
                page, size, SortHelper.parseSortOptions(sortBy)
        ));
        return ResponseEntity.ok(mapMany(products));
    }
}
