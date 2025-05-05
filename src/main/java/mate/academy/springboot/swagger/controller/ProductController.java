package mate.academy.springboot.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@Tag(name = "product", description = "The Product Manager API.")
public class ProductController {
    private final ProductService productService;
    private final SortUtil sortUtil;
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> productMapper;

    @Operation(summary = "Add product.", description = "Add product to DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created.",
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input.") })
    @PostMapping
    public ProductResponseDto add(@Parameter(
            description = "Product to add. Cannot null or empty.",
            required = true, schema = @Schema(implementation = ProductRequestDto.class))
                                      @Valid @RequestBody ProductRequestDto productRequestDto) {
        return productMapper.mapToDto(
                productService.save(productMapper.mapToModel(productRequestDto)));
    }

    @Operation(summary = "Get a product by its ID from DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content) })
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable("id")
                                      @Parameter(name = "id", description = "Product ID.",
                                              example = "1") Long id) {
        return productMapper.mapToDto(productService.getById(id));
    }

    @Operation(summary = "Delete product by its ID from DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the product.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")
                           @Parameter(name = "id",
                                   description = "Product id", example = "1") Long id) {
        productService.delete(id);
    }

    @Operation(summary = "Update an existing product",
            description = "Update info about product", tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied."),
            @ApiResponse(responseCode = "404", description = "Product not found."),
            @ApiResponse(responseCode = "405", description = "Validation exception.") })
    @PutMapping(value = "/products/{id}", consumes = { "application/json" })
    public void update(
            @Parameter(description = "ID of the product to be update.", required = true)
            @PathVariable long id,
            @Parameter(description = "Product to update.",
                    required = true, schema = @Schema(implementation = ProductRequestDto.class))
            @Valid @RequestBody ProductRequestDto productRequestDto) {
        productService.update(id, productMapper.mapToModel(productRequestDto));
    }

    @Operation(summary = "Enumeration all products.", description = "List of products from DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation.",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = Product.class)))) })
    @GetMapping(value = "/all", produces = { "application/json" })
    public List<ProductResponseDto> findAll(
            @Parameter(description = "Number of pages. Cannot be empty.",
                    required = true, schema = @Schema(type = "integer", defaultValue = "15"))
            @RequestParam int numberPage,
            @Parameter(description = "Count of entries on page. Cannot be empty.",
                    required = true, schema = @Schema(type = "integer", defaultValue = "50"))
            @RequestParam int countOnPage,
            @Parameter(description = "Name of sorting column. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "id"))
            @RequestParam String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(numberPage, countOnPage, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "List products by price range.",
            description = "Returns filtered products by price. ", tags = { "contact" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation.",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found.") })
    @GetMapping("/range")
    public List<ProductResponseDto> findByPriceRange(
            @RequestParam String from,
            @RequestParam String to,
            @Parameter(description = "Number of pages. Cannot be empty.",
                    required = true, schema = @Schema(type = "integer", defaultValue = "15"))
            @RequestParam int numberPage,
            @Parameter(description = "Count of entries on page. Cannot be empty.",
                    required = true, schema = @Schema(type = "integer", defaultValue = "50"))
            @RequestParam int countOnPage,
            @Parameter(description = "Name of sorting column. Cannot be empty.",
                    required = true, schema = @Schema(type = "String", defaultValue = "id"))
            @RequestParam String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(numberPage, countOnPage, sort);
        return productService.findAllByPriceBetween(
                new BigDecimal(from.trim()), new BigDecimal(to.trim()),
                pageRequest)
                .stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
