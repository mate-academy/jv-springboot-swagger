package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.SortingService;
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
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> productMapper;
    private final SortingService sortingService;

    public ProductController(ProductService productService,
                             DtoMapper<ProductRequestDto, ProductResponseDto,
                                     Product> productMapper,
                             SortingService sortingService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.sortingService = sortingService;
    }

    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product =
                productService.add(productMapper.mapToModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = productMapper.mapToModel(productRequestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.update(product));
    }

    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "3") Integer size,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortingService.create(page, size, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam(defaultValue = "3") Integer size,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "id") String sortBy) {
        Sort sort = sortingService.create(page, size, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
