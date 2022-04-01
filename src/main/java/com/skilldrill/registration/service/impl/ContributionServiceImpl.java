package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.ContributionDto;
import com.skilldrill.registration.exceptions.FileStorageException;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.ContributionMapper;
import com.skilldrill.registration.model.Contribution;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.ContributionRepository;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;

    private final ContributionMapper contributionMapper;

    private final UserRepository userRepository;

    private final MessageSource messageSource;


    @Autowired
    public ContributionServiceImpl(ContributionRepository contributionRepository, UserRepository userRepository, ContributionMapper contributionMapper, MessageSource messageSource) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
        this.contributionMapper = contributionMapper;
        this.messageSource = messageSource;
    }

    @Override
    public Contribution addContribution(ContributionDto contributionDto, String userName) {
        Contribution contribution = contributionMapper.toContribution(contributionDto);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        contribution.setDateOfContribution(String.valueOf(LocalDate.now()));
        contribution.setUser(user);
        return contributionRepository.save(contribution);
    }

    @Override
    public ContributionDto uploadImage(String contributionName, MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();

        try {
            if (!imageFile.isEmpty()) {
                Contribution contribution = contributionRepository.findByContributionName(contributionName)
                        .orElseThrow(() -> new NotFoundException("No contribution present"));
                contribution.setImage(imageFile.getBytes());
                return contributionMapper.toDto(contributionRepository.save(contribution));
            } else {
                throw new NotFoundException("Image not found");
            }
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public ContributionDto updateContribution(String contributionName, ContributionDto contributionDto) {
        Contribution contribution = contributionRepository.findByContributionName(contributionName)
                .orElseThrow(() -> new NotFoundException("No contribution present"));
        contribution.setFileLink(contributionDto.getFileLink());
        contribution.setProblems(contributionDto.getProblems());
        contributionRepository.save(contribution);
        return contributionMapper.toDto(contribution);
    }

    @Override
    public void archiveContribution(String contributionName) {
        Contribution contribution = contributionRepository.findByContributionName(contributionName)
                .orElseThrow(() -> new NotFoundException("No contribution present"));
        contributionRepository.delete(contribution);
    }

    @Override
    public List<Contribution> getAllContributionsOfUser(String userName) {
        List<Contribution> contributionList = contributionRepository.findAll();
        return contributionList.stream()
                .filter(contribution -> contribution.getUser().getEmail().equals(userName))
                .collect(Collectors.toList());
    }
}
