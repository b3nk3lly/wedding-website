package ca.b3nk3lly.wedding_website_backend.service;

import ca.b3nk3lly.wedding_website_backend.converter.GroupResponseDtoConverter;
import ca.b3nk3lly.wedding_website_backend.dto.GroupCreationDto;
import ca.b3nk3lly.wedding_website_backend.dto.GroupResponseDto;
import ca.b3nk3lly.wedding_website_backend.entity.Group;
import ca.b3nk3lly.wedding_website_backend.entity.User;
import ca.b3nk3lly.wedding_website_backend.repository.GroupRepository;
import ca.b3nk3lly.wedding_website_backend.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public GroupService(GroupRepository groupRepository, BCryptPasswordEncoder passwordEncoder) {
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PreAuthorize("hasRole('ADMIN')")
    public GroupResponseDto createOne(GroupCreationDto dto) {
        String hashedPassword = passwordEncoder.encode(dto.password());

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(hashedPassword);

        Group group = new Group();
        group.setUser(user);
        group.setName(dto.name());

        return GroupResponseDtoConverter.toDto(groupRepository.save(group));
    }
}
