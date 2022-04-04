package com.skilldrill.registration.repository;

import com.skilldrill.registration.model.Categories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Categories,ObjectId> {

    Optional<Categories> findByCategoryName(String categoryName);
}
