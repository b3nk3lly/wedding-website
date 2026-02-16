package ca.b3nk3lly.wedding_website_backend.controller;

import ca.b3nk3lly.wedding_website_backend.annotation.WithMockAuthenticatedUser;
import ca.b3nk3lly.wedding_website_backend.service.GroupService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @MockitoBean
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockAuthenticatedUser(userId = 2)
    void testGetGroupForUser() throws Exception {
        mockMvc.perform(get("/groups?userId=2")).andExpect(status().isOk());
        Mockito.verify(groupService, Mockito.times(1)).findByUserId(2);
    }
}
