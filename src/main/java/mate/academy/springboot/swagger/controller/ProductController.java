package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
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
public class ProductController {
    private final RequestDtoMapper<ProductRequestDto, Product> requestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseDtoMapper;
    private final ProductService productService;

    @ApiOperation(value = "Add new Product")
    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.add(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @ApiOperation(value = "Get Product by ID")
    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(productService.get(id));
    }

    @ApiOperation(value = "Delete product by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @ApiOperation(value = "Update Product by ID")
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productService.update(id, requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(product);
    }

    @ApiOperation(value = "Get all products (with pagination and sorting)")
    @GetMapping
    public List<ProductResponseDto> findAll(
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "title:ASC") String sortBy) {
        String[] fieldAndDirection = sortBy.split(":");
        Sort sort = Sort.by(Sort.Direction.valueOf(fieldAndDirection[1]), fieldAndDirection[0]);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAll(pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get Products with price between given amounts "
            + "(with pagination and sorting)")
    @GetMapping("/by-price")
    public List<ProductResponseDto> findAllByPriceBetween(
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "title:ASC") String sortBy,
            @RequestParam Long from,
            @RequestParam Long to) {
        String[] fieldAndDirection = sortBy.split(":");
        Sort sort = Sort.by(Sort.Direction.valueOf(fieldAndDirection[1]), fieldAndDirection[0]);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return productService.getAllByPriceBetween(from, to, pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
