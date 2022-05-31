package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductDtoRequest;
import mate.academy.springboot.swagger.dto.ProductDtoResponse;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.PageRequestUtil;
import org.springframework.data.domain.PageRequest;
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
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ResponseEntity<ProductDtoResponse> create(@Valid @RequestBody ProductDtoRequest dto) {
        Product newProduct = mapper.mapToEntity(dto);
        Product product = service.save(newProduct);
        ProductDtoResponse response = mapper.mapToDto(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseEntity<ProductDtoResponse> get(
            @ApiParam(value = "product id") @PathVariable Long id) {
        Product product = service.getById(id);
        ProductDtoResponse response = mapper.mapToDto(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ResponseEntity<ProductDtoResponse> update(
            @ApiParam(value = "product id") @PathVariable Long id,
            @RequestBody ProductDtoRequest dto) {
        Product product = mapper.mapToEntity(dto).setId(id);
        ProductDtoResponse response = mapper.mapToDto(service.save(product));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public ResponseEntity<ProductDtoResponse> delete(
            @ApiParam(value = "product id") @PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Find paged and sorted products")
    public ResponseEntity<List<ProductDtoResponse>> findAllSortedByPriceOrTitle(
            @ApiParam(value = "default count is 20")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam(value = "default page is 0")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "default sort field is id")
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(count, page, sortBy);
        List<ProductDtoResponse> responses = service
                .findAllSortedByPriceOrTitle(pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/price/between")
    @ApiOperation(value = "Find paged and sorted products between price")
    public ResponseEntity<List<ProductDtoResponse>> findAllBetweenPriceSortedByPriceOrTitle(
            @ApiParam(value = "from price")
            @RequestParam BigDecimal from,
            @ApiParam(value = "to price")
            @RequestParam BigDecimal to,
            @ApiParam(value = "default count is 20")
            @RequestParam(defaultValue = "20") Integer count,
            @ApiParam(value = "default page is 0")
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "default sort field is id")
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(count, page, sortBy);
        List<ProductDtoResponse> responses = service
                .findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
