package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.Mapper;
import mate.academy.springboot.swagger.service.parser.ParamParser;
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
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final Mapper<Product, ProductRequestDto, ProductResponseDto> mapper;

    @GetMapping("/{id}")
    @ApiOperation(value = "Find Product by ID from DB")
    public ProductResponseDto findById(@PathVariable Long id) {
        return mapper.toDto(productService.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create Product and save it to DB")
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return mapper.toDto(productService.save(mapper.toModel(productRequestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Product by ID from DB")
    public boolean delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Product by ID with new values of this product in DB")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        return mapper.toDto(product);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all Product from DB by custom params")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "10")
                                               @ApiParam(value = "default value is 10")
                                               Integer count,
                                           @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "default value is 0")
                                                Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "default value is id")
                                               String sortBy) {
        Sort sort = Sort.by(ParamParser.sortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam BigDecimal from,
                                                  @RequestParam BigDecimal to,
                                                  @RequestParam (defaultValue = "10")
                                                      @ApiParam(value = "default value is 10")
                                                      Integer count,
                                                  @RequestParam (defaultValue = "0")
                                                      @ApiParam(value = "default value is 0")
                                                      Integer page,
                                                  @RequestParam (defaultValue = "id")
                                                      @ApiParam(value = "default value is id")
                                                      String sortBy) {
        Sort sort = Sort.by(ParamParser.sortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
