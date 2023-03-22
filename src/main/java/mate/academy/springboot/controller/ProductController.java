package mate.academy.springboot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.dto.ProductRequestDto;
import mate.academy.springboot.dto.ProductResponseDto;
import mate.academy.springboot.dto.mapper.RequestDtoMapper;
import mate.academy.springboot.dto.mapper.ResponseDtoMapper;
import mate.academy.springboot.model.Product;
import mate.academy.springboot.service.ProductService;
import mate.academy.springboot.util.SortUtil;
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

    @ApiOperation(value = "create a new product")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return responseMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("{/id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = requestMapper.toModel(requestDto);
        product.setId(id);
        return responseMapper.toDto(productService.save(product));
    }

    @ApiOperation(value = "Get products list")
    @GetMapping
    public List<ProductResponseDto> findAllProducts(@RequestParam(defaultValue = "20")
                                    @ApiParam(value = "default value is 20") Integer count,
                                    @RequestParam(defaultValue = "0")
                                    @ApiParam(value = "default value is '0'") Integer page,
                                    @RequestParam (defaultValue = "id")
                                    @ApiParam(value = "default sorting by - id") String sortBy) {
        Sort sort = sortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllProducts(pageRequest).stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get products list price range")
    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam
                                      @ApiParam(value = "price from") BigDecimal from,
                                      @RequestParam
                                      @ApiParam(value = "price to") BigDecimal to,
                                      @RequestParam(defaultValue = "20")
                                      @ApiParam(value = "default value is 20") Integer count,
                                      @RequestParam(defaultValue = "0")
                                      @ApiParam(value = "default value is '0'") Integer page,
                                      @RequestParam (defaultValue = "id")
                                      @ApiParam(value = "default sorting by - id") String sortBy) {
        Sort sort = sortUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByProductsPriceBetween(from, to, pageRequest)
                .stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }
}
