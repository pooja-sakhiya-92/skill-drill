package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.ContributionDto;
import com.skilldrill.registration.model.Contribution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContributionMapper extends ForeignKeyMapper {

    ContributionDto toDto(Contribution contribution);

    Contribution toContribution(ContributionDto contributionDto);

}
