package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.dto.GroupCreationDto;
import ca.b3nk3lly.wedding_website_backend.dto.GroupResponseDto;
import ca.b3nk3lly.wedding_website_backend.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(params = "userId")
    public GroupResponseDto findGroupByUserId(@RequestParam Integer userId) {
        return groupService.findByUserId(userId);
    }

    @GetMapping
    public List<GroupResponseDto> findAllGroups() {
        return groupService.findAll();
    }

    @PostMapping
    public GroupResponseDto createGroup(@RequestBody GroupCreationDto dto) {
        return this.groupService.createOne(dto);
    }
}
