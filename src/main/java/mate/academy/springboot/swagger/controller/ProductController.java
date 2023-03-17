package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
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

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseMapper;
    private final SortUtil sortUtil;

    @ApiOperation(value = "Add a new product.")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(product);
    }

    @ApiOperation(value = "Find a product by id and show info about it.")
    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return responseMapper.toDto(productService.findById(id));
    }

    @ApiOperation(value = "Delete a product by id.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "Update a product by id")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto
    ) {
        Product product = requestMapper.toModel(requestDto);
        product.setId(id);
        return responseMapper.toDto(productService.save(product));
    }

    @ApiOperation(value = "Get all products sorted and with pagination")
    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "20")
                                               @ApiParam (value = "default value is `20`")
                                               Integer count,
                                           @RequestParam(defaultValue = "0")
                                               @ApiParam (value = "default value is `0`")
                                               Integer page,
                                           @RequestParam(defaultValue = "title")
                                               @ApiParam (value = "default value is `title`")
                                               String sortBy) {
        Sort sort = sortUtil.getSorter(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all products filtered by price, sorted and with pagination.")
    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllBetweenPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam(defaultValue = "20")
                                                  @ApiParam (value = "default value is `20`")
                                                           Integer count,
                                                  @RequestParam(defaultValue = "0")
                                                  @ApiParam (value = "default value is `0`")
                                                           Integer page,
                                                  @RequestParam(defaultValue = "title")
                                                  @ApiParam (value = "default value is `title`")
                                                           String sortBy) {
        Sort sort = sortUtil.getSorter(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}
