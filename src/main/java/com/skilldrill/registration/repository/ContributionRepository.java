package com.skilldrill.registration.repository;

import com.skilldrill.registration.dto.ContributionDto;
import com.skilldrill.registration.model.Contribution;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContributionRepository extends MongoRepository<Contribution, ObjectId> {

    @Query("{contribution_name:?0}")
    Optional<Contribution> findByContributionName(String contributionName);

    @Query("{$and:[{contribution_name:?0},{email:?1}]}")
    Optional<Contribution> findByContributionNameAndUserName(String contributionName,String email);

    @Query("{email:?0}")
    List<Contribution> findByUserName(String userName);

}
