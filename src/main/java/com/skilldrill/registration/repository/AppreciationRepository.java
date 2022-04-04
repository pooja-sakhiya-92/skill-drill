package com.skilldrill.registration.repository;

import com.skilldrill.registration.model.Appreciation;
import com.skilldrill.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppreciationRepository extends MongoRepository<Appreciation,String> {

    @Query("{praise:?0}")
    Optional<Appreciation> findByPraise(String praise);

    List<Appreciation> findByUser(User user);

}
