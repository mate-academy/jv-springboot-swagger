package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.SortUtil;
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

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private SortUtil sortUtil;
    private DtoMapper<Product,
            ProductResponseDto,
            ProductRequestDto> mapper;

    @GetMapping
    List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                     @RequestParam (defaultValue = "0") Integer page,
                                     @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = Sort.by(sortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam (defaultValue = "20") Integer count,
            @RequestParam (defaultValue = "0") Integer page,
            @RequestParam (defaultValue = "id") String sortBy,
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to) {
        Sort sort = Sort.by(sortUtil.parse(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    ProductResponseDto findById(@PathVariable Long id) {
        return mapper.toDto(productService.find(id));
    }

    @PostMapping
    ProductResponseDto save(@RequestBody ProductRequestDto dto) {
        Product product = mapper.toModel(dto);
        return mapper.toDto(productService.save(product));
    }

    @PutMapping
    void update(@RequestBody ProductRequestDto dto) {
        productService.save(mapper.toModel(dto));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
