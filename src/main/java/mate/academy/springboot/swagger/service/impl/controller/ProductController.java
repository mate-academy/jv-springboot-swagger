package mate.academy.springboot.swagger.service.impl.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortService;
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
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final SortService sortService;

    @PostMapping
    @ApiOperation(value = "Add a product to DB")
    public ProductResponseDto add(
            @Parameter(description = "Product to add", required = true,
                    schema = @Schema(implementation = ProductRequestDto.class))
            @RequestBody ProductRequestDto requestDto) {
        return responseDtoMapper
                .mapToDto(productService.add(requestDtoMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by id")
    public ProductResponseDto update(
            @PathVariable Long id,
            @Parameter(description = "Product to update", required = true,
                    schema = @Schema(implementation = ProductRequestDto.class))
            @RequestBody ProductRequestDto requestDto) {
        Product product = requestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        return responseDtoMapper.mapToDto(productService.add(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Find all products with pagination")
    public List<ProductResponseDto> findAll(
                @RequestParam (defaultValue = "20")
                @ApiParam(value = "Gets product amount per page. Default value is `20`")
                Integer amount,
                @RequestParam (defaultValue = "0")
                @ApiParam(value = "Gets page number. Default value is `0`")
                Integer page,
                @RequestParam (defaultValue = "id")
                @ApiParam(value = "Default sort is sort by 'id' DESC")
                String sortBy) {
        Sort sort = Sort.by(sortService.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, amount, sort);
        return productService.findAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Find all products by price between with pagination")
    public List<ProductResponseDto> findAllByPrice(
                @ApiParam(value = "Lowest price ")
                @RequestParam
                BigDecimal from,
                @ApiParam(value = "Highest price")
                @RequestParam
                BigDecimal to,
                @RequestParam (defaultValue = "20")
                @ApiParam(value = "Gets product amount per page. Default value is `20`")
                Integer amount,
                @RequestParam (defaultValue = "0")
                @ApiParam(value = "Gets page number. Default value is `0`")
                Integer page,
                @RequestParam (defaultValue = "id")
                @ApiParam(value = "Default sort is sort by 'id' DESC")
                String sortBy) {
        Sort sort = Sort.by(sortService.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, amount, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
