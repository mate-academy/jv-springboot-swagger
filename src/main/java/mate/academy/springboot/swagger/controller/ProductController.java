package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.UrlSortParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UrlSortParser urlParser;

    @PostMapping
    @ApiOperation(value = "create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.toProductModel(productRequestDto);
        return productMapper.toProductResponseDto(productService.create(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.toProductResponseDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto
                                             productRequestDto) {
        Product product = productMapper.toProductModel(productRequestDto);
        product.setId(id);
        return productMapper.toProductResponseDto(productService.create(product));
    }

    @GetMapping
    @ApiOperation(value = "get all products in pageable format with sorting")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = createPageRequest(page, pageSize, sortBy);
        return productService.getAllWithParam(pageRequest)
                .stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products by price between in pageable format with sorting")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam(required = false, defaultValue = "0")
                                                             Integer page,
                                                         @RequestParam(required = false, defaultValue = "20")
                                                             Integer pageSize,
                                                         @RequestParam(required = false, defaultValue = "id")
                                                             String sortBy) {
        PageRequest pageRequest = createPageRequest(page, pageSize, sortBy);

        return productService.getAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    private PageRequest createPageRequest(Integer page, Integer pageSize, String sortBy) {
        Sort sort = urlParser.getSort(sortBy);
        return PageRequest.of(page, pageSize, sort);
    }
}
