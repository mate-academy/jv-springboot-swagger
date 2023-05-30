package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.impl.ProductDtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.service.ProductService;
import mate.academy.springboot.swagger.util.SortService;
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
    private final ProductDtoMapper dtoMapper;
    private final ProductService productService;
    private final SortService sortService;

    public ProductController(ProductDtoMapper dtoMapper,
                             ProductService productService,
                             SortService sortService) {
        this.dtoMapper = dtoMapper;
        this.productService = productService;
        this.sortService = sortService;
    }

    @PostMapping
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return dtoMapper.toDto(productService.save(dtoMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return dtoMapper.toDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateById(@PathVariable Long id,
                                         @RequestBody ProductRequestDto requestDto) {
        Product product = dtoMapper.toModel(requestDto);
        product.setId(id);
        return dtoMapper.toDto(productService.update(product));
    }

    @GetMapping("/findAll")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(orders));
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/findAll/prices")
    public List<ProductResponseDto> findAllByPrices(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "20") Integer count,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = sortService.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, Sort.by(orders));
        return productService.findAllByPrice(from, to, pageRequest).stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
