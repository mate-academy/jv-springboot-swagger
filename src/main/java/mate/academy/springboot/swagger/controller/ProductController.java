package mate.academy.springboot.swagger.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.RequestDtoMapper;
import mate.academy.springboot.swagger.service.mapper.ResponseDtoMapper;
import mate.academy.springboot.swagger.util.Parser;
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
public class ProductController {
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;
    private final ProductService productService;

    public ProductController(RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper,
                             ResponseDtoMapper<ProductResponseDto, Product>
                                     productResponseDtoMapper, ProductService productService) {
        this.productRequestDtoMapper = productRequestDtoMapper;
        this.productResponseDtoMapper = productResponseDtoMapper;
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        return productResponseDtoMapper.mapToDto(productService
                .save(productRequestDtoMapper.mapToModel(dto)));
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productResponseDtoMapper.mapToDto(productService.get(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    //update Product
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto dto) {
        Product product = productRequestDtoMapper.mapToModel(dto);
        product.setId(id);
        return productResponseDtoMapper.mapToDto(productService.save(product));
    }

    //get all products with pagination and ability to sort by price or by title in ASC or DESC order
    @GetMapping
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "10") Integer count,
                                            @RequestParam (defaultValue = "0") Integer page,
                                            @RequestParam (defaultValue = "title") String sortBy) {
        Sort sort = Sort.by(Parser.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //get all products where price is between two values received as a RequestParam inputs.
    // Add pagination and ability to sort by price or by title in ASC or DESC order.
}






