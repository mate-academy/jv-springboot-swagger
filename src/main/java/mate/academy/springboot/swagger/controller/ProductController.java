package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
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

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> dtoMapper;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product from database by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return dtoMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product from database by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PostMapping
    @ApiOperation(value = "Create product in database")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        return dtoMapper.mapToDto(
                productService.save(dtoMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product in database by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = dtoMapper.mapToModel(requestDto);
        product.setId(id);
        return dtoMapper.mapToDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get all products")
    public List<ProductResponseDto> getAll(@RequestParam(defaultValue = "10")
                                               @ApiParam(value = "default value is 10")
                                               Integer count,
                                           @RequestParam(defaultValue = "0")
                                               @ApiParam(value = "default value is 0")
                                               Integer page,
                                           @RequestParam(defaultValue = "id")
                                               @ApiParam(value = "default value is id")
                                               String sortBy) {
        Sort sort = Sort.by(SortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all product between price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam(defaultValue = "10")
                                                      @ApiParam(value = "default value is 10")
                                                      Integer count,
                                                  @RequestParam(defaultValue = "0")
                                                      @ApiParam(value = "default value is 0")
                                                      Integer page,
                                                  @RequestParam(defaultValue = "id")
                                                      @ApiParam(value = "default value is id")
                                                      String sortBy) {
        Sort sort = Sort.by(SortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
