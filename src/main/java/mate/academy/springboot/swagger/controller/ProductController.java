package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final Mapper<Product, ProductRequestDto, ProductResponseDto> mapper;
    private final ParamParser paramParser;

    @GetMapping("/{id}")
    @ApiOperation(value = "Find Product by ID from DB")
    public ProductResponseDto findById(@PathVariable Long id) {
        return mapper.toDto(productService.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create Product and save it to DB")
    public ProductResponseDto save(@RequestBody(description = "Product to add.")
                                       ProductRequestDto productRequestDto) {
        return mapper.toDto(productService.save(mapper.toModel(productRequestDto)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Product by ID from DB")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Product by ID with new values of this product in DB")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody(description = "Product to update")
                                     ProductRequestDto productRequestDto) {
        Product product = productService.update(mapper.toModel(productRequestDto));
        product.setId(id);
        return mapper.toDto(product);
    }

    @GetMapping
    @ApiOperation(value = "Get all Products from DB by custom params")
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "10")
                                               @ApiParam(value = "The page size")
                                               Integer count,
                                           @RequestParam (defaultValue = "0")
                                                @ApiParam(value = "The page number")
                                                Integer page,
                                           @RequestParam (defaultValue = "id")
                                               @ApiParam(value = "The field to sort by")
                                               String sortBy) {
        Sort sort = Sort.by(paramParser.sortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get all Products from DB by price")
    public List<ProductResponseDto> getAllByPrice(@RequestParam (defaultValue = "0")
                                                      @ApiParam(value = "Minimal price.")
                                                      BigDecimal from,
                                                  @RequestParam (defaultValue = "99999")
                                                    @ApiParam(value = "Maximum price.")
                                                    BigDecimal to,
                                                  @RequestParam (defaultValue = "10")
                                                      @ApiParam(value = "The page size")
                                                      Integer count,
                                                  @RequestParam (defaultValue = "0")
                                                      @ApiParam(value = "The page number")
                                                      Integer page,
                                                  @RequestParam (defaultValue = "id")
                                                      @ApiParam(value = "The field to sort by")
                                                      String sortBy) {
        Sort sort = Sort.by(paramParser.sortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
