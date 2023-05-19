package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.DtoMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@Tag(name = "product", description = "The Product Manager API")
public class ProductController {
    private final ProductService productService;
    private final SortUtil sortUtil;
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> productMapper;

    @Operation(summary = "Add Product", description = "Add Product to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Product already exists") })
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(productMapper.mapToModel(productRequestDto));
        return productMapper.mapToDto(product);
    }

    @Operation(summary = "Get a Product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @Operation(summary = "Delete Product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the Product",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @Operation(summary = "Enumeration all Products", description = "List of Products from DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = Product.class)))) })
    @GetMapping(value = "/all", produces = { "application/json", "application/xml" })
    public List<ProductResponseDto> findAll(
            @Parameter(description = "Number of pages. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "15"))
            @RequestParam String numberPage,
            @Parameter(description = "Count of entries on page. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "50"))
            @RequestParam String countOnPage,
            @Parameter(description = "Name of sorting column. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "id"))
            @RequestParam String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(numberPage.trim()),
                Integer.parseInt(countOnPage.trim()), sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "List Products by price range", description = "Returns filtered by price ",
            tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found") })
    @GetMapping("/range")
    public List<ProductResponseDto> findByPriceRange(
            @RequestParam String from,
            @RequestParam String to,
            @Parameter(description = "Number of pages. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "15"))
            @RequestParam String numberPage,
            @Parameter(description = "Count of entries on page. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "50"))
            @RequestParam String countOnPage,
            @Parameter(description = "Name of sorting column. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "id"))
            @RequestParam String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(numberPage.trim()),
                Integer.parseInt(countOnPage.trim()), sort);
        return productService.findAllByPriceBetween(
                new BigDecimal(from.trim()), new BigDecimal(to.trim()),
                pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
