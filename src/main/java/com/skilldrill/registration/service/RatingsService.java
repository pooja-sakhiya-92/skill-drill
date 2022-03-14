package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.ContributionDto;
import com.skilldrill.registration.dto.RatingsDto;
import com.skilldrill.registration.model.Contribution;
import com.skilldrill.registration.model.Ratings;
import java.util.List;

public interface RatingsService {

    RatingsDto addRatings(RatingsDto ratingsDto, String userName);

    RatingsDto updateRatings(String userName, RatingsDto ratingsDto);

    void deleteRatings(String userName);

    RatingsDto getAllRatingsOfUser(String userName);

}

