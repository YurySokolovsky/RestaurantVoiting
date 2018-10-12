package uv.sokolovsky.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uv.sokolovsky.TestUtil.userAuth;
import static uv.sokolovsky.testData.UserTestData.ADMIN;
import static uv.sokolovsky.testData.UserTestData.USER1;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    public void testRestaurants() throws Exception {
        mockMvc.perform(get("/restaurants")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants.jsp"));
    }

    @Test
    public void testDishes() throws Exception {
        mockMvc.perform(get("/dishes")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("dishes"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/dishes.jsp"));
    }

    @Test
    public void testVotes() throws Exception {
        mockMvc.perform(get("/votes")
                .with(userAuth(USER1)))
                .andDo(print())
                .andExpect(view().name("votes"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/votes.jsp"));
    }

    @Test
    public void testRoot() throws Exception {
        mockMvc.perform(get("/")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("votes"));
    }

    @Test
    public void testProfile() throws Exception {
        mockMvc.perform(get("/profile")
                .with(userAuth(USER1)))
                .andDo(print())
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(view().name("profile"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"));
    }

    @Test
    public void testUnAuth() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}