package com.telnov.software_design.reactive_app.repository;

import com.telnov.software_design.reactive_app.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepository extends ReactiveMongoRepository<User, Long> {
}
