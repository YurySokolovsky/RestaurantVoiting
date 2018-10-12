package uv.sokolovsky.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uv.sokolovsky.model.Vote;

import java.time.LocalDate;

import static uv.sokolovsky.testData.VoteTestData.*;
import static uv.sokolovsky.testData.UserTestData.ADMIN;


public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    protected VoteService service;

    @Test
    public void create() throws Exception {
        Vote created = getCreated();
        service.create(created);
        assertMatch(service.getAllByDate(LocalDate.now()), VOTE3, VOTE4, created);
    }

    @Test
    public void update() throws Exception {
        Vote updated = getUpdated();
        service.update(updated);
        assertMatch(service.getAllByDate(LocalDate.now()), updated, VOTE4);
    }

    @Test
    public void getOneByDate() throws Exception {
        assertMatch(service.getOneByDate(ADMIN, LocalDate.now()), VOTE4);
    }

    @Test
    public void getAllByDate() throws Exception {
        assertMatch(service.getAllByDate(LocalDate.of(2018, 05, 05)), VOTE1, VOTE2);
    }
}