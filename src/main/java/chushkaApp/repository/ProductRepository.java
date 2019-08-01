package chushkaApp.repository;

import chushkaApp.domain.entites.Product;

public interface ProductRepository extends GenericRepository<Product, String> {

    Product findByProductName(String name);
}
