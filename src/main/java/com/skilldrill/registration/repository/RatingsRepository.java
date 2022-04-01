package com.skilldrill.registration.repository;

import com.skilldrill.registration.model.Ratings;
import com.skilldrill.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingsRepository extends MongoRepository<Ratings, String> {

    Optional<Ratings> findByUser(User user);


}
