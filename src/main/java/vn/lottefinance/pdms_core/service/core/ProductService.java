package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO.Response> findAll();
    ProductDTO.Response findById(Long id);
    ProductDTO.Response create(ProductDTO.Request request);
    ProductDTO.Response update(Long id, ProductDTO.Request request);
    void delete(Long id);
}
