package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.dto.mapper.ProductMapper;
import mate.academy.springboot.swagger.exception.CustomGlobalExceptionHandler;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/products")
public class ProductController extends CustomGlobalExceptionHandler {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductMapper productMapper,
                             ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new product.")
    public ProductResponseDto create(ProductRequestDto requestDto) {
        Product product = productService.create(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by id.")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productMapper.toDto(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by id.")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping()
    @ApiOperation(value = "Update a product.")
    public ProductResponseDto update(ProductRequestDto requestDto) {
        Product product = productService.update(productMapper.toModel(requestDto));
        return productMapper.toDto(product);
    }

    @GetMapping()
    @ApiOperation(value = "find all products. You can use Pagination and Sorting!")
    public List<ProductResponseDto> findAll(@RequestParam (defaultValue = "20") Integer count,
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "id") String sortBy) {
        Sort sort = SortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productService.findAll(pageRequest).stream()
                .map(productMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = "Find all products between two prices. "
            + "You can use Pagination and Sorting!")
    public List<ProductResponseDto> getAllByPrice(@RequestParam (defaultValue = "20") Integer count,
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "id") String sortBy,
                                           @RequestParam Map<String, String> params) {
        Sort sort = SortUtil.getSort(sortBy);
        Pageable pageable = PageRequest.of(page, count, sort);
        return productService.findAllByPrice(params, pageable).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
