package mate.academy.springboot.swagger.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.springboot.swagger.dto.ProductRequestDto;
import mate.academy.springboot.swagger.dto.ProductResponseDto;
import mate.academy.springboot.swagger.mapper.ProductMapper;
import mate.academy.springboot.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @Autowired
    public ProductController(ProductService service,
                             ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(service.create(mapper.toModel(dto)));
    }

    @GetMapping("/get")
    public ProductResponseDto get(@RequestParam Long id) {
        return mapper.toDto(service.get(id));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        service.delete(id);
    }

    @PutMapping("/update")
    public ProductResponseDto update(@RequestBody ProductRequestDto dto) {
        return mapper.toDto(service.update(mapper.toModel(dto)));
    }

    @GetMapping
    private List<ProductResponseDto> getAll(
            @RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "4") String count,
            @RequestParam(defaultValue = "title:ASC") String sortBy,
            @RequestParam(required = false) String priceFrom,
            @RequestParam(required = false) String priceTo
    ) {
        return service.getAll(page, count, sortBy, priceFrom, priceTo)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
