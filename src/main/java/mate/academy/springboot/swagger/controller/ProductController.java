package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortParser;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SortParser sortParser;
    private final ProductMapper productMapper;

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        productService.add(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                      @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.mapToDto(product);
    }

    @GetMapping()
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "0")
                                                @ApiParam(value = "default value is 0")
                                                Integer page,
                                            @RequestParam (defaultValue = "4")
                                                @ApiParam(value = "default value is 4")
                                                Integer count,
                                            @RequestParam (defaultValue = "id")
                                                @ApiParam(value
                                                        = "default value "
                                                        + "is sorting by id DESC order")
                                                String sortBy) {
        return productService.findAll(PageRequest.of(page, count, sortParser.parse(sortBy)))
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    @ApiOperation(value = "Get all products where price between 2 values")
    public List<ProductResponseDto> getAllPriceBetween(@RequestParam
                                                           @ApiParam(value
                                                                   = "start value for price")
                                                           BigDecimal from,
                                                       @RequestParam
                                                       @ApiParam(value = "end value for price")
                                                       BigDecimal to,
                                                       @RequestParam (defaultValue = "0")
                                                           @ApiParam(value = "default value is 0")
                                                           Integer page,
                                                       @RequestParam (defaultValue = "4")
                                                           @ApiParam(value = "default value is 4")
                                                           Integer count,
                                                       @RequestParam (defaultValue = "id")
                                                           @ApiParam(value
                                                                   = "default value is "
                                                                   + "sorting by id DESC order")
                                                           String sortBy) {
        return productService.getAllByPriceBetween(from, to,
                        PageRequest.of(page, count, sortParser.parse(sortBy)))
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    @ApiOperation(value = "Inject products to db for testing purposes")
    public void saveProducts() {
        Product iphone8 = new Product();
        iphone8.setTitle("Asus 8");
        iphone8.setPrice(BigDecimal.valueOf(999));
        productService.add(iphone8);

        Product iphone9 = new Product();
        iphone9.setTitle("Uphone 9");
        iphone9.setPrice(BigDecimal.valueOf(799));
        productService.add(iphone9);

        Product macBook8 = new Product();
        macBook8.setTitle("TBook 8");
        macBook8.setPrice(BigDecimal.valueOf(1999));
        productService.add(macBook8);

        Product macBook9 = new Product();
        macBook9.setTitle("MacBook 9");
        macBook9.setPrice(BigDecimal.valueOf(1799));
        productService.add(macBook9);
    }
}
