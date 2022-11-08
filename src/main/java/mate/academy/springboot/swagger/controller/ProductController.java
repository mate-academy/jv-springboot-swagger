package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.ProductSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final ProductSort productSort;

    public ProductController(ProductService productService,
            RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper,
            ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper,
            ProductSort productSort) {
        this.productService = productService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.productSort = productSort;
    }

    @PostMapping
    public ProductResponseDto create(@RequestParam ProductRequestDto dto) {
        return responseDtoMapper.mapToDto(productService.create(requestDtoMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam(defaultValue = "3") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "price") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, productSort.productSort(sortBy));
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> findByPriceSorted(@RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam(defaultValue = "3") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "price") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, count, productSort.productSort(sortBy));
        return productService.findAllByPrice(from, to, pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());

    }
}
