package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.ContributionDto;
import com.skilldrill.registration.model.Contribution;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContributionService {

    Contribution addContribution(ContributionDto contributionDto,String userName);

    ContributionDto uploadImage(String contributionName, MultipartFile imageFile);

    ContributionDto updateContribution(String contributionName,ContributionDto contributionDto);

    void archiveContribution(String contributionName);

    List<Contribution> getAllContributionsOfUser(String userName);
}
