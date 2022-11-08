package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
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
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    public ProductController(ProductService productService,
                             ProductDtoMapper productDtoMapper) {
        this.productService = productService;
        this.productDtoMapper = productDtoMapper;
    }

    @PostMapping //+
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(
                productDtoMapper.mapToModel(requestDto));
        return productDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestParam ProductRequestDto requestDto) {
        Product product = productDtoMapper.mapToModel(requestDto);
        product.setId(id);
        productService.update(product);
        return productDtoMapper.mapToDto(product);
    }

    @GetMapping()
    public List<ProductResponseDto> getAllProducts(
            @RequestParam(defaultValue = "2") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to) {
        return productService.getAllByPriceBetween(from, to)
                .stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
