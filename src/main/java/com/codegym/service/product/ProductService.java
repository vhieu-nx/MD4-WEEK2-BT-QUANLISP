package com.codegym.service.product;

import com.codegym.exception.NotFoundException;
import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService implements IProductService {
    //JPA
//    @PersistenceContext
//    private EntityManager entityManager;

    //hibernate
//    @Autowired
//    private EntityManager entityManager;
//    @Autowired
//    private SessionFactory sessionFactory;
    @Autowired
    private IProductRepository productRepository;


    @Override
    public List<Product> findALl() {
        return (List<Product>) productRepository.findAll();
    }
    //hibernate
//        Session session = sessionFactory.openSession();
//        String query = "Select p from Product as p";
//        TypedQuery<Product> queryFind = session.createQuery(query, Product.class);
//        return queryFind.getResultList();

    //JPA
//        String query = "Select p from Product as p";
//        TypedQuery<com.codegym.model.Product> queryFind = entityManager.createQuery(query, com.codegym.model.Product.class);
//        return queryFind.getResultList();
    @Override
    public Page<Product> findALl(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

//        @Override
//    public List<Product> findTop5() {
//        return productRepository.findTop5ByOrderByPriceDesc();
//    }

    @Override
    public List<Product> findTop5() {
        return productRepository.getProductByPriceExp(5);
    }

    @Override
    public List<Product> findTop5ByDate() {
        return productRepository.findTop5ByOrderByDateDesc();
    }

    @Override
    public int getSumPrice() {
        int sum = 0;
        sum = productRepository.getSumPrice();
        return sum;
    }


    @Override
    public Product findById(Long id) throws NotFoundException {
        Product product = productRepository.findOne(id);
        if (product != null) return product;
        else throw new NotFoundException();
    }
    //hibernate
//        Session session = sessionFactory.openSession();
//        String query = "select c from Product as c where c.id = :id";
//        TypedQuery<Product> queryFindId = session.createQuery(query, Product.class);
//        queryFindId.setParameter("id", id);
//        return queryFindId.getSingleResult();

    //JPA
//        String query = "select c from Product as c where c.id = :id";
//        TypedQuery<com.codegym.model.Product> queryFindId = entityManager.createQuery(query, com.codegym.model.Product.class);
//        queryFindId.setParameter("id", id);
//        return queryFindId.getSingleResult();

//    public void update(Product model) {
    //hibernate
//        Session session = null;
//        Transaction transaction = null;
//        try {
//            session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            session.update(model);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }

    //JPA
//        if (model.getId() != 0) {
//            entityManager.merge(model);
//        }
//    }


    @Override
    public Product save(Product product) {
        //hibernate
//        Session session = null;
//        Transaction transaction = null;
//        try {
//            session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            session.save(model);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }

        //JPA
//        if (model.getId() != 0) {
//            entityManager.merge(model);
//        } else {
//            entityManager.persist(model);
//        }
        return productRepository.save(product);
    }


    @Override
    public void deleteById(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> findByProductName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public List<Product> findByCategoryName(Long id) {
        return productRepository.findProductByCategoryName(id);
    }
    //hibernate
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.remove(findById(id));
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        } finally {
//            if (session != null) ;
//            session.close();
//        }

    //JPA
//        Product product = findById(id);
//        if (product != null) {
//            entityManager.remove(product);
//        }


    //JPA
//    @Override
//    public List<Product> searchByName(String name) {
//        String query = "select p from Product as p where p.name like :name";
//        TypedQuery queryFind = entityManager.createQuery(query, com.codegym.model.Product.class);
//        String nameFind ="%" + name + "%";
//        queryFind.setParameter("name", nameFind);
//        return queryFind.getResultList();
//    }
}
