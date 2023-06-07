package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.request.RequestProductDto;
import mate.academy.springboot.swagger.dto.response.ResponseProductDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final SortService sortService;
    private final DtoMapper<Product, RequestProductDto, ResponseProductDto> productMapper;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ResponseProductDto create(@RequestBody RequestProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseProductDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ResponseProductDto update(@PathVariable Long id,
                                     @RequestBody RequestProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        product.setId(id);
        return productMapper.toDto(productService.save(product));
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ResponseProductDto> findAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(defaultValue = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(defaultValue = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(defaultValue = "default sorting is ASC") String sortBy) {
        Sort sort = Sort.by(sortService.sort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list where price between")
    public List<ResponseProductDto> getAllByPriceBetween(
            @RequestParam BigDecimal from,
            @RequestParam BigDecimal to,
            @RequestParam (defaultValue = "20")
                @ApiParam(defaultValue = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
                @ApiParam(defaultValue = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(defaultValue = "default sorting is ASC") String sortBy) {
        Sort sort = Sort.by(sortService.sort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return convertModelsListToDto(productService.findAllByPriceBetween(from, to, pageRequest));
    }

    private List<ResponseProductDto> convertModelsListToDto(List<Product> products) {
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
