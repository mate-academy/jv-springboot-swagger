package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.requests.ProductRequestsDto;
import mate.academy.springboot.swagger.model.dto.responses.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortingService;
import mate.academy.springboot.swagger.service.mapper.ProductMapper;
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

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final SortingService sortingService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             SortingService sortingService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.sortingService = sortingService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ProductResponseDto save(@RequestBody ProductRequestsDto productRequestsDto) {
        return productMapper.mapToDto(productService
                .save(productMapper.mapToModel(productRequestsDto)));
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        productService.remove(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestsDto productRequestsDto) {
        Product product = productMapper.mapToModel(productRequestsDto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                           @ApiParam(value = "Default value is 20")
                                           Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "Default value is 0")
                                           Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value = "Default value is - id")
                                           String sortBy) {
        Sort sort = Sort.by(sortingService.sortBy(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price-between")
    @ApiOperation(value = "Get all products where price is between")
    public List<ProductResponseDto> getAll(@RequestParam BigDecimal from,
                                           @RequestParam BigDecimal to,
                                           @RequestParam(defaultValue = "20")
                                           @ApiParam(value = "Default value is 20")
                                           Integer count,
                                           @RequestParam(defaultValue = "0")
                                           @ApiParam(value = "0")
                                           Integer page,
                                           @RequestParam(defaultValue = "id")
                                           @ApiParam(value = "Default value is - id")
                                           String sortBy) {
        Sort sort = Sort.by(sortingService.sortBy(sortBy));
        PageRequest pageRequest = PageRequest.of(page,count,sort);
        return productService.getAll(from,to, pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
