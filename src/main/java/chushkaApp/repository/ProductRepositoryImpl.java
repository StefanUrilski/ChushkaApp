package chushkaApp.repository;

import chushkaApp.domain.entites.Product;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private EntityManager entityManager;

    public ProductRepositoryImpl() {
        this.entityManager = Persistence
                .createEntityManagerFactory("chushka")
                .createEntityManager();
    }

    @Override
    public Product save(Product entity) {
        this.entityManager.getTransaction().begin();

        entityManager.persist(entity);

        this.entityManager.getTransaction().commit();

        return entity;
    }

    @Override
    public Product findById(String id) {
        this.entityManager.getTransaction().begin();

        Product product = entityManager
                .createQuery("select p from products as p where p.id = :id", Product.class)
                .setParameter(":id", id)
                .getSingleResult();

        this.entityManager.getTransaction().commit();
        return product;
    }

    @Override
    public Product findByProductName(String name) {
        this.entityManager.getTransaction().begin();

        Product product = entityManager
                .createQuery("select p from products as p where p.name = ?1", Product.class)
                .setParameter(1, name)
                .getSingleResult();

        this.entityManager.getTransaction().commit();
        return product;
    }

    @Override
    public List<Product> findAll() {
        this.entityManager.getTransaction().begin();

        List<Product> allProducts = entityManager.createQuery("select p from products as p", Product.class)
                .getResultList();

        this.entityManager.getTransaction().commit();
        return allProducts;
    }
}
