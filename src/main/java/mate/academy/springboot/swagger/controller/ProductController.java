package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "creates a new product")
    private ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productDtoMapper.toProduct(productRequestDto);
        product = productService.save(product);
        return productDtoMapper.toResponseDto(product);
    }

    @GetMapping
    @ApiOperation(value = "gets product from DB by id")
    private ProductResponseDto getById(@RequestParam Long id) {
        Product product = productService.getById(id);
        return productDtoMapper.toResponseDto(product);
    }

    @DeleteMapping
    @ApiOperation(value = "deletes product from DB by id")
    private void deleteById(@RequestParam Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    @ApiOperation(value = "updates product in DB. Product object should be passed as json body")
    private void update(@RequestBody Product product) {
        productService.update(product);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "gets all products in DB. "
            + "Accepts page size, page number and sortBy parameters")
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
    @ApiOperation(value = "gets all products in DB where price is within the specified range. "
            + "Accepts page size, page number and sortBy parameters")
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