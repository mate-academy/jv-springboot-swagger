package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.model.dto.ProductRequestDto;
import mate.academy.springboot.swagger.model.dto.ProductResponseDto;
import mate.academy.springboot.swagger.service.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.PaginationSortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper mapper;
    private final ProductService productService;
    private final PaginationSortUtil paginationSortUtil;

    @PostMapping
    @ApiOperation(value = "Create a new product in DB")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.add(mapper.mapToProduct(productRequestDto));
        return mapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return mapper.mapToDto(productService.get(id));
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update info about product")
    public ProductResponseDto update(@RequestBody ProductRequestDto productRequestDto,
                                     @PathVariable Long id) {
        Product product = productService.get(id);
        product.setPrice(productRequestDto.getPrice());
        product.setTitle(productRequestDto.getTitle());
        return mapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete product from DB")
    void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Find all products with pagination and sorting by fields")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "10")
                                                    @ApiParam(value = "Default value is 10")
                                                        Integer count,
                                            @RequestParam (defaultValue = "0")
                                                    @ApiParam(value = "Default value is 0")
                                                        Integer page,
                                            @RequestParam (defaultValue = "id")
                                                    @ApiParam(value = "Default field is 'id'")
                                                        String sortBy) {
        PageRequest pageRequest = paginationSortUtil.getPageRequest(count, page, sortBy);
        return productService.findAll(pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Find all products by price range with pagination and sorting by fields")
    public List<ProductResponseDto> getByPriceBetween(@RequestParam BigDecimal from,
                                                      @RequestParam BigDecimal to,
                                                      @RequestParam (defaultValue = "10")
                                                          @ApiParam(value = "Default value is 10")
                                                                  Integer count,
                                                      @RequestParam (defaultValue = "0")
                                                          @ApiParam(value = "Default value is 0")
                                                                  Integer page,
                                                      @RequestParam (defaultValue = "id")
                                                          @ApiParam(value = "Default field is 'id'")
                                                                  String sortBy) {
        PageRequest pageRequest = paginationSortUtil.getPageRequest(count, page, sortBy);
        return productService.getByPriceBetween(from, to, pageRequest).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
