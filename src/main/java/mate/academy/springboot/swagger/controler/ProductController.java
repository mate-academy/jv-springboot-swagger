package mate.academy.springboot.swagger.controler;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.mapper.RequestMapperDto;
import mate.academy.springboot.swagger.dto.mapper.ResponseMapperDto;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortProductUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final RequestMapperDto<ProductRequestDto, Product> requestMapperDto;
    private final ResponseMapperDto<ProductResponseDto, Product> responseMapperDto;
    private final SortProductUtil sortProductUtil;

    @PostMapping
    @ApiOperation(value = "Create a new product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        return responseMapperDto.toDto(productService.create(requestMapperDto.toModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseMapperDto.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = requestMapperDto.toModel(requestDto);
        product.setId(id);
        return responseMapperDto.toDto(productService.update(product));
    }

    @GetMapping
    @ApiOperation(value = "Get product's list")
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`")
            Integer page,
            @RequestParam(defaultValue = "title")
            String sortBy) {
        Sort sort = Sort.by(sortProductUtil.getSortingProducts(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAll(pageRequest)
                .stream()
                .map(responseMapperDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Get product's list with 2 limiting parameters price: from and to")
    public List<ProductResponseDto> findAllByPrice(
            @RequestParam
            @ApiParam(value = "the lowest price")
            BigDecimal from,
            @RequestParam
            @ApiParam(value = "the biggest price")
            BigDecimal to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "default value is `20`")
            Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "default value is `0`")
            Integer page,
            @RequestParam(defaultValue = "title")
            String sortBy) {
        Sort sort = Sort.by(sortProductUtil.getSortingProducts(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest)
                .stream()
                .map(responseMapperDto::toDto)
                .collect(Collectors.toList());
    }
}

