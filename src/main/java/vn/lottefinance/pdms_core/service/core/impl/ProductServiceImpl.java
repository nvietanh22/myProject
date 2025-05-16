package vn.lottefinance.pdms_core.service.core.impl;

import vn.lottefinance.pdms_core.domain.Product;
import vn.lottefinance.pdms_core.service.core.dto.ProductDTO;
import vn.lottefinance.pdms_core.repository.ProductRepository;
import vn.lottefinance.pdms_core.service.core.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProductDTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public ProductDTO.Response create(ProductDTO.Request request) {
        Product entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public ProductDTO.Response update(Long id, ProductDTO.Request request) {
        Product entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ProductDTO.Response toResponse(Product entity) {
        return ProductDTO.Response.builder()
            .id(entity.getId())
            .name(entity.getName())
            .code(entity.getCode())
            .build();
    }

    private Product toEntity(ProductDTO.Request request) {
        return Product.builder()
            .name(request.getName())
            .code(request.getCode())
            .build();
    }
}
