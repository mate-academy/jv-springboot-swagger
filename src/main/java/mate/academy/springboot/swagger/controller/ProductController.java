package mate.academy.springboot.swagger.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import mate.academy.springboot.swagger.dto.RequestDto;
import mate.academy.springboot.swagger.dto.ResponseDto;
import mate.academy.springboot.swagger.model.Product;
import mate.academy.springboot.swagger.service.ProductService;
import mate.academy.springboot.swagger.service.mapper.DtoMapper;
import mate.academy.springboot.swagger.util.SortUtil;
import org.springframework.data.domain.PageRequest;
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
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final DtoMapper<RequestDto, ResponseDto, Product> mapper;

    @PostMapping("/create")
    @ApiOperation("create a new product")
    public ResponseDto create(@RequestBody(description = "Product to add.", required = true,
            content = @Content(schema = @Schema(implementation = RequestDto.class)))
                                  @Valid RequestDto requestDto) {
        Product product = productService.save(mapper.toModel(requestDto));
        return mapper.toDto(product);
    }

    @GetMapping("/{id}")
    @ApiOperation("get product")
    public ResponseDto get(@PathVariable Long id) {
        return mapper.toDto(productService.get(id));
    }

    @GetMapping
    @ApiOperation("get all products with pagination and ability "
            + "to sort by price or by title in ASC or DESC order")
    public List<ResponseDto> findAll(@ApiParam(value = "page size", defaultValue = "20")
                                         @RequestParam(defaultValue = "20") Integer count,
                                     @ApiParam(value = "page number", defaultValue = "0")
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @ApiParam(value = "sort by", defaultValue = "price")
                                     @RequestParam(defaultValue = "price") String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAll(request).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-price")
    @ApiOperation("get all products where price is between two values received as a "
            + "RequestParam inputs, also with pagination and ability to sort by price or by title "
            + "in ASC or DESC order")
    public List<ResponseDto> findAllByPriceBetween(
            @RequestParam @ApiParam("price from") Long from,
            @RequestParam @ApiParam("price to") Long to,
            @RequestParam(defaultValue = "20")
            @ApiParam(value = "page size", defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0")
            @ApiParam(value = "page number", defaultValue = "0") Integer page,
            @RequestParam @ApiParam(value = "sort by", defaultValue = "price") String sortBy) {
        Sort sort = Sort.by(SortUtil.parse(sortBy));
        PageRequest request = PageRequest.of(page, count, sort);
        return productService.getAllByPriceBetween(from, to, request).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    @ApiOperation("update product")
    public ResponseDto update(@RequestParam Long id,
                              @RequestBody(description = "Product to update.", required = true,
                                      content = @Content(schema = @Schema(
                                              implementation = RequestDto.class)))
                              @Valid RequestDto productRequestDto) {
        Product product = mapper.toModel(productRequestDto);
        product.setId(id);
        return mapper.toDto(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete product")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
