package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.mapper.DtoMapper;
import mate.academy.springboot.swagger.dto.request.ProductRequestDto;
import mate.academy.springboot.swagger.dto.response.ProductResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.util.ProductControllerSortUtil;
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
    private final DtoMapper<ProductRequestDto, ProductResponseDto, Product> dtoMapper;
    private final ProductControllerSortUtil sortUtil;

    @PostMapping
    @ApiOperation(value = " Adds new product to DB")
    public ProductResponseDto add(@RequestBody ProductRequestDto requestDto) {
        return dtoMapper.mapToDto(productService.add(dtoMapper.mapToModel(requestDto)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = " Gets product by id")
    public ProductResponseDto get(@PathVariable Long id) {
        return dtoMapper.mapToDto(productService.get(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = " Updates product information by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = dtoMapper.mapToModel(requestDto);
        product.setId(id);
        return dtoMapper.mapToDto(productService.update(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = " Deletes product by id")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = " Retrieves all products with pagination")
    public List<ProductResponseDto> findAll(
            @RequestParam (defaultValue = "20")
                @ApiParam(value = " Gets product amount per page, default 20") Integer amount,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = " Gets page number, default 0") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = " Gets product parameter to implement sorting, "
                        + "format \"parameter:ORDER\", default sort is sortBy=id:ASC")
                String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, amount, sort);
        return productService.findAll(pageRequest).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation(value = " Retrieves products by price range with pagination")
    public List<ProductResponseDto> findAllByPrice(
            @RequestParam
                @ApiParam(value = " Gets lowest price for filter") BigDecimal from,
            @RequestParam
                @ApiParam(value = " Gets highest price for filter") BigDecimal to,
            @RequestParam (defaultValue = "20")
                @ApiParam(value = " Gets product amount per page, default 20") Integer amount,
            @RequestParam (defaultValue = "0")
                @ApiParam(value = " Gets page number, default 0") Integer page,
            @RequestParam (defaultValue = "id")
                @ApiParam(value = " Gets product parameter to implement sorting, "
                        + "format \"parameter:ORDER\", default sort is sortBy=id:ASC")
                String sortBy) {
        Sort sort = Sort.by(sortUtil.getSort(sortBy));
        PageRequest pageRequest = PageRequest.of(page, amount, sort);
        return productService.findAllByPricing(from, to, pageRequest).stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
