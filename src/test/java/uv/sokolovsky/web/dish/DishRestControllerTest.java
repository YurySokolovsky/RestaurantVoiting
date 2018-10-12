package uv.sokolovsky.web.dish;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uv.sokolovsky.model.Dish;
import uv.sokolovsky.service.DishService;
import uv.sokolovsky.util.exception.ErrorType;
import uv.sokolovsky.web.AbstractControllerTest;
import uv.sokolovsky.web.json.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uv.sokolovsky.TestUtil.*;
import static uv.sokolovsky.testData.DishTestData.*;
import static uv.sokolovsky.testData.UserTestData.ADMIN;
import static uv.sokolovsky.testData.RestaurantTestData.RESTAURANT_ID;
import static uv.sokolovsky.testData.RestaurantTestData.RESTAURANT1;
import static uv.sokolovsky.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_DISH;


public class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    private DishService service;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH1));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + DISH_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(RESTAURANT1), DISH2, DISH3, DISH4);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = getUpdated();

        mockMvc.perform(put(REST_URL + (DISH_ID + 6))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        assertMatch(service.get(DISH_ID + 6), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Dish created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)));

        Dish returned = readFromJson(action, Dish.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getByDate(LocalDate.now(), RESTAURANT1), created, DISH3, DISH4);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArray(DISH3, DISH4));
    }

    @Test
    public void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "getDishesByDate")
                .param("date", "2018-05-05")
                .param("restaurantId", String.valueOf(RESTAURANT_ID))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJsonArray(DISH1, DISH2));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Dish invalid = new Dish(null, new BigDecimal("1.00"), RESTAURANT1, LocalDate.now());
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Dish invalid = new Dish(DISH_ID+6, null, new BigDecimal("1.00"), RESTAURANT1, LocalDate.now());
        mockMvc.perform(put(REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    public void testUpdateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish(DISH_ID+6, "<script>alert(123)</script>", new BigDecimal("1.00"), RESTAURANT1, LocalDate.now());
        mockMvc.perform(put(REST_URL + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testUpdateDuplicate() throws Exception {
        Dish invalid = new Dish(DISH_ID+7, "Каша гречневая", new BigDecimal("2.70") , RESTAURANT1 ,LocalDate.now());

        mockMvc.perform(put(REST_URL + (DISH_ID+7))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_DISH));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateDuplicate() throws Exception {
        Dish invalid = new Dish("Каша гречневая", new BigDecimal("2.70") , RESTAURANT1 ,LocalDate.now());
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_DISH));
    }
}
