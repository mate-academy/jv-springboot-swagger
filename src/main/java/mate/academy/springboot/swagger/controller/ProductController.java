package mate.academy.springboot.swagger.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductDtoMapper;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductDtoMapper productDtoMapper;
    private final ProductService productService;
    private final SortUtil sortUtil;

    public ProductController(ProductDtoMapper productDtoMapper,
                             ProductService productService, SortUtil sortUtil) {
        this.productDtoMapper = productDtoMapper;
        this.productService = productService;
        this.sortUtil = sortUtil;
    }

    @PostMapping
    private ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productDtoMapper.toProduct(productRequestDto);
        product = productService.save(product);
        return productDtoMapper.toResponseDto(product);
    }

    @GetMapping
    private ProductResponseDto getById(@RequestParam Long id) {
        Product product = productService.getById(id);
        return productDtoMapper.toResponseDto(product);
    }

    @DeleteMapping
    private void deleteById(@RequestParam Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    private void update(@RequestBody Product product) {
        productService.update(product);
    }

    @GetMapping("/getAll")
    private List<ProductResponseDto> getAll(@RequestParam (defaultValue = "3") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = sortUtil.parse(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest).stream()
                .map(productDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }



    @GetMapping("/getAllByPriceBetween")
    private List<ProductResponseDto> getAllByPriceBetween(@RequestParam (defaultValue = "3") Integer count,
                                                          @RequestParam (defaultValue = "0") Integer page,
                                                          @RequestParam (defaultValue = "id") String sortBy,
                                                          @RequestParam BigDecimal from,
                                                          @RequestParam BigDecimal to) {
        PageRequest pageRequest = PageRequest.of(page, count, sortUtil.parse(sortBy));
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}