package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.springboot.swagger.dto.ProductDto;
import mate.academy.springboot.swagger.mapper.Dto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortParamParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final SortParamParser sortParamParser;
    private final Dto dto;

    public ProductController(ProductService productService, SortParamParser sortParamParser,
                             Dto dto) {
        this.productService = productService;
        this.sortParamParser = sortParamParser;
        this.dto = dto;
    }

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ResponseEntity<ProductDto> add(@RequestBody ProductDto productDto) {
        Product add = productService.add(dto.toModel(productDto));
        return ResponseEntity.ok(dto.toDto(add));
    }

    @ApiOperation(value = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(dto.toDto(productService.get(id)));
    }

    @ApiOperation(value = "Delete product by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ApiOperation(value = "Update product")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @RequestBody ProductDto productDto) {
        Product update = dto.toModel(productDto);
        update.setId(id);
        productService.update(update);
        return ResponseEntity.ok(dto.toDto(update));
    }

    @ApiOperation(value = "Get all products")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size,
                                                  @RequestParam(defaultValue = "id")
                                                       String sortBy) {
        Sort parse = sortParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, parse);
        return ResponseEntity.ok(dto.toDtoList(productService.getAll(pageRequest)));
    }

    @ApiOperation(value = "Get all products by price")
    @GetMapping("/byPrice")
    public ResponseEntity<List<ProductDto>> getAllByPrice(@RequestParam BigDecimal from,
                                                           @RequestParam BigDecimal to,
                                                           @RequestParam(defaultValue = "0")
                                                               Integer page,
                                                           @RequestParam(defaultValue = "20")
                                                               Integer size,
                                                           @RequestParam(defaultValue = "id")
                                                               String sortBy) {
        Sort parse = sortParamParser.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, parse);
        return ResponseEntity.ok(dto.toDtoList(productService
                .getAllByPrice(from, to, pageRequest)));
    }
}
