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
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.SortOrderUtil;
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
    private final SortOrderUtil sortOrderUtil;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.save(requestDtoMapper.mapToModel(productRequestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        Product product = productService.get(id);
        return responseDtoMapper.mapToDto(product);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a product by id")
    public ProductResponseDto update(
            @PathVariable Long id,
            @RequestBody ProductRequestDto requestDto
    ) {
        Product product = requestDtoMapper.mapToModel(requestDto);
        product.setId(id);
        Product updatedProduct = productService.save(product);
        return responseDtoMapper.mapToDto(updatedProduct);
    }

    @GetMapping
    @ApiOperation(value = "Get products list")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default sorting by 'id'") String sortBy
    ) {
        Sort sort = sortOrderUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get products list, where price "
            + "in range from 'from' to 'to' inclusively")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam("min%price")
            @ApiParam(value = "'from' price") BigDecimal minPrice,
            @RequestParam("max%price")
            @ApiParam(value = "'to' price") BigDecimal maxPrice,
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "default value is '20'") Integer count,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "default value is '0'") Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "default sorting by 'id'") String sortBy
    ) {
        Sort sort = sortOrderUtil.sort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAllByPriceBetween(minPrice, maxPrice, pageRequest)
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
