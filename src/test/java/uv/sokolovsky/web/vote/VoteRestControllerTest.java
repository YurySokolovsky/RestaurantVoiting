package uv.sokolovsky.web.vote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import uv.sokolovsky.model.Vote;
import uv.sokolovsky.service.VoteService;
import uv.sokolovsky.util.DateTimeUtil;
import uv.sokolovsky.util.RestaurantUtil;
import uv.sokolovsky.web.AbstractControllerTest;
import uv.sokolovsky.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uv.sokolovsky.TestUtil.*;
import static uv.sokolovsky.testData.RestaurantTestData.restaurants;
import static uv.sokolovsky.testData.UserTestData.*;
import static uv.sokolovsky.testData.VoteTestData.*;
import static uv.sokolovsky.testData.RestaurantTestData.RESTAURANT_ID;

public class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';
    private static final String REST_URL1 = REST_URL + "restaurant/";

    @Autowired
    private VoteService service;

    @Test
    public void testGetAllUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdate() throws Exception {
        //DateTimeUtil.setDate(LocalDate.now());
        Vote updated = getUpdated();
        mockMvc.perform(put(REST_URL1 + RESTAURANT_ID)
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk());

        assertMatch(service.getAllByDate(LocalDate.now()), updated, VOTE4);
    }

    @Test
    public void testCreate() throws Exception {
        //DateTimeUtil.setDate(LocalDate.now());
        Vote created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(USER1)));

        Vote returned = readFromJson(action, Vote.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAllByDate(LocalDate.now()), VOTE3, VOTE4, created);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RestaurantUtil.getRestaurantWithVotes(restaurants, service.getAllByDate(LocalDate.now()))));
    }

    @Test
    public void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "getVotesByDate")
                .param("date", "2018-05-05")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(RestaurantUtil.getRestaurantWithVotes(restaurants, service.getAllByDate(LocalDate.of(2018,05,05)))));
    }
}
