package chushkaApp.service;

import chushkaApp.domain.entites.Product;
import chushkaApp.domain.entites.Type;
import chushkaApp.models.service.ProductServiceModel;
import chushkaApp.repository.ProductRepository;
import chushkaApp.util.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Inject
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public void saveProduct(ProductServiceModel productServiceModel) {
        Product product = modelMapper.map(productServiceModel, Product.class);
        product.setType(Type.valueOf(productServiceModel.getType()));

        productRepository.save(product);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    ProductServiceModel productModule = modelMapper.map(product, ProductServiceModel.class);
                    productModule.setType(product.getType().toString());

                    return productModule;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductByName(String name) {
        Product product = productRepository.findByProductName(name);
        ProductServiceModel productServiceModel = modelMapper.map(product, ProductServiceModel.class);
        productServiceModel.setType(product.getType().toString());

        return productServiceModel;
    }
}
