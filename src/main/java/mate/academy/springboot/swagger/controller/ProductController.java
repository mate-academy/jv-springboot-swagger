package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortOrderParser;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("products")
public class ProductController {
    private static final Set<String> IGNORE_REQUEST_PARAM;
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;
    private final SortOrderParser sortOrderParser;

    static {
        IGNORE_REQUEST_PARAM = Set.of("page", "size", "sort");
    }

    @PostMapping
    @ApiOperation(value = "Create new product")
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto requestDto) {
        Product product = productRequestDtoMapper.toModel(requestDto);
        return productResponseDtoMapper.toDto(productService.save(product));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productResponseDtoMapper.toDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @Valid @RequestBody ProductRequestDto requestDto) {
        Product product = productRequestDtoMapper.toModel(requestDto);
        product.setId(id);
        return productResponseDtoMapper.toDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "") Map<String, String> params,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sortOrderParser.parseSortOrder(sort));
        return productService.findAll(params, IGNORE_REQUEST_PARAM, pageRequest).stream()
                .map(productResponseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
