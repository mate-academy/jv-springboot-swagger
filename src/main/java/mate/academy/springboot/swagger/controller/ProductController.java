package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final SortUtil sortUtil;

    @PostMapping
    @ApiOperation("Save a new product to the database")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/by-price")
    @ApiOperation("Get products by range of price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam
            BigDecimal from,
            @RequestParam
            BigDecimal to,
            @RequestParam (defaultValue = "2")
            @ApiParam(value = "default value is '3'")
            Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '1'")
            Integer page,
            @RequestParam (defaultValue = "id")
            String sortBy) {
        Sort sort = Sort.by(sortUtil.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping()
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "2")
                                               @ApiParam(value = "default value is '3'")
                                               Integer count,
                                           @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "default value is '1'")
                                                Integer page,
                                           @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sortUtil.getOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    public String inject() {
        Product product1 = new Product();
        product1.setTitle("Bread");
        product1.setPrice(new BigDecimal("12"));
        productService.save(product1);
        Product product2 = new Product();
        product2.setTitle("Wine");
        product2.setPrice(new BigDecimal("15"));
        productService.save(product2);
        Product product3 = new Product();
        product3.setTitle("Milk");
        product3.setPrice(new BigDecimal("9"));
        productService.save(product3);
        Product product4 = new Product();
        product4.setTitle("Prawns");
        product4.setPrice(new BigDecimal("30"));
        productService.save(product4);
        Product product5 = new Product();
        product5.setTitle("Meat");
        product5.setPrice(new BigDecimal("25"));
        productService.save(product5);
        return "Products was created";
    }
}
