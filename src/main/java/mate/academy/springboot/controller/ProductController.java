package mate.academy.springboot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.model.Product;
import mate.academy.springboot.model.dto.ProductRequestDto;
import mate.academy.springboot.model.dto.ProductResponseDto;
import mate.academy.springboot.model.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.model.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.service.ProductService;
import mate.academy.springboot.util.SortOrderUtil;
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
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final SortOrderUtil sortOrderUtil;

    @PostMapping
    @ApiOperation(value = "Add a new product")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(requestDtoMapper.toModel(requestDto));
        return responseDtoMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.getById(id);
        return responseDtoMapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update product by id")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        Product product = requestDtoMapper.toModel(requestDto);
        product.setId(id);
        return responseDtoMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "get products list")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20")
                                                @ApiParam(value = "default value is 20")
                                                Integer count,
                                            @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "default value is 0")
                                            Integer page,
                                            @RequestParam (defaultValue = "title")
                                                @ApiParam(value = "default value is 'title'")
                                                String sortBy) {
        Sort sort = sortOrderUtil.getSorted(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "get products list with price between values")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to,
                                                         @RequestParam (defaultValue = "20")
                                                         @ApiParam(value = "default value is 20")
                                                             Integer count,
                                                         @RequestParam (defaultValue = "0")
                                                         @ApiParam(value = "default value is 0")
                                                             Integer page,
                                                         @RequestParam (defaultValue = "title")
                                                         @ApiParam(value = "default value is title")
                                                             String sortBy) {
        Sort sort = sortOrderUtil.getSorted(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
