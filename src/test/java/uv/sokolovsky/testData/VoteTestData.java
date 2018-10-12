package uv.sokolovsky.testData;

import uv.sokolovsky.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static uv.sokolovsky.model.AbstractBaseEntity.START_SEQ;

import static uv.sokolovsky.testData.RestaurantTestData.*;
import static uv.sokolovsky.testData.UserTestData.*;

public class VoteTestData {
    public static final int VOTE_ID = START_SEQ + 18;

    public static final Vote VOTE1 = new Vote(VOTE_ID, RESTAURANT1, USER1, LocalDate.of(2018, 05,05));
    public static final Vote VOTE2 = new Vote(VOTE_ID+1, RESTAURANT3, USER2, LocalDate.of(2018, 05,05));
    public static final Vote VOTE3 = new Vote(VOTE_ID+2, RESTAURANT2, USER2, LocalDate.now());
    public static final Vote VOTE4 = new Vote(VOTE_ID+3, RESTAURANT2, ADMIN, LocalDate.now());

    public static Vote getCreated() {
        return new Vote (RESTAURANT1, USER1, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote (VOTE_ID+2 ,RESTAURANT1, USER2, LocalDate.now());
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
