package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.converter.GroupResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.dto.GroupResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Group;
import ca.b3nk3lly.wedding_website_backend.repository.GroupRepository;
import ca.b3nk3lly.wedding_website_backend.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupResponseDto findByUserId(Integer userId) {
        Group group = groupRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Couldn't find group with userId " + userId));

        if (!SecurityUtils.canUserAccessGroup(group)) {
            throw new AccessDeniedException("Current user can't access group with ID " + group.getId());
        }

        return GroupResponseDtoConverter.toDto(group);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<GroupResponseDto> findAll() {
        return groupRepository.findAll().stream().map(GroupResponseDtoConverter::toDto).toList();
    }
}
