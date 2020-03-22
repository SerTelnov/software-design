package com.telnov.software_design.reactive_app.repository;

import com.telnov.software_design.reactive_app.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCrudRepository extends ReactiveMongoRepository<Product, Long> {
}
