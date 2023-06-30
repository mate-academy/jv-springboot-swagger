package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<ProductRequestDto, Product, ProductResponseDto> productMapper;
    private final SortUtil sortUtil;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ProductResponseDto add(@RequestBody(description = "", required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDto.class,
                    defaultValue = "{ \n"
                    + "    \"title\":\"banana\", \n"
                    + "    \"price\":\"10.99\", \n"
                    + "}")))
                                      @Valid ProductRequestDto productRequestDto) {
        return productMapper
                .toDto(productService.save(productMapper.toModel(productRequestDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find product by product id")
    public ProductResponseDto getProductById(
            @Parameter(description = "id of product to be searched")
            @PathVariable Long id) {
        return productMapper.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by product id")
    public void deleteProductById(@Parameter(description = "id of product to be deleted")
                                      @PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product information by product id")
    public ProductResponseDto update(@Parameter(description = "id of product to be updated")
                                         @PathVariable Long id,
                                     @RequestBody(description = "", required = true,
                                             content = @Content(schema =
                                             @Schema(implementation = ProductRequestDto.class)))
                                     @Valid ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        product.setId(id);
        productService.save(product);
        return productMapper.toDto(product);
    }

    @GetMapping
    @Operation(summary = "Get product list")
    public List<ProductResponseDto> findAllProducts(
            @RequestParam (defaultValue = "25")
            @Parameter(description = "Number of products per page",
                    required = true) Integer count,
            @RequestParam (defaultValue = "0")
            @Parameter(description = "Number of pages",
                    required = true) Integer page,
            @RequestParam (defaultValue = "id")
            @Parameter(description = "Parameters to sort by",
                    required = true) String sortBy) {
        List<Sort.Order> orders = sortUtil.getOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllProducts(pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @Operation(summary = "Get product list by price between")
    public List<ProductResponseDto> findAllProductsByPriceBetween(
            @RequestParam @Parameter(description = "Start price to sort from",
                    required = true) BigDecimal fromPrice,
            @RequestParam @Parameter(description = "Finish price to sort to",
                    required = true) BigDecimal toPrice,
            @Parameter(description = "Number of products per page", required = true)
            @RequestParam (defaultValue = "25") Integer count,
            @Parameter(description = "Number of pages", required = true)
            @RequestParam (defaultValue = "0") Integer page,
            @Parameter(description = "Parameters to sort by", required = true)
            @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = sortUtil.getOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllProductsByPriceBetween(fromPrice, toPrice, pageRequest)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
