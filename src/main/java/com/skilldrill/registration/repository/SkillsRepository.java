package com.skilldrill.registration.repository;

import com.skilldrill.registration.model.Categories;
import com.skilldrill.registration.model.Skills;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillsRepository extends MongoRepository<Skills, ObjectId> {

    Optional<Skills> findBySkillName(String skillName);

    List<Skills> findByCategories(Categories categories);
}
