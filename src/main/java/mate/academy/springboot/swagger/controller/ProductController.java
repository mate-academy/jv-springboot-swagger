package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.SortOrderService;
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
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private ProductService productService;
    private ProductMapper productMapper;
    private SortOrderService sortOrderService;

    @ApiOperation(value = "create new product")
    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.create(productMapper.fromDto(requestDto));
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "get product by id")
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "delete product by id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ApiOperation(value = "update product")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.fromDto(requestDto);
        product.setId(id);
        productService.update(product);
        return productMapper.toDto(product);
    }

    @ApiOperation(value = "get all products")
    @GetMapping
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
            @ApiParam(value = "Quantity rows per page. Default value is 20")
            Integer amount,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "Number of the page. Default value is 0")
            Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "Sorting field name. Default value is 'id'")
            String sortBy) {
        List<Sort.Order> orders = sortOrderService.sortOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, amount, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "get products between the prices")
    @GetMapping("by-price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam
            @ApiParam(value = "Lower boundary of the price range")
            int from,
            @RequestParam
            @ApiParam(value = "Upper boundary of the price range")
            int to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "Amount of rows per page. Default value is 20")
            Integer amount,
            @RequestParam (defaultValue = "0")
            @ApiParam(value = "PAhe number. Default value is 0")
            Integer page,
            @RequestParam (defaultValue = "id")
            @ApiParam(value = "Sorting field name. Default value is 'id'")
            String sortBy) {
        List<Sort.Order> orders = sortOrderService.sortOrders(sortBy);
        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, amount, sort);
        return productService.findAllByPriceBetween(from, to, pageRequest).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
