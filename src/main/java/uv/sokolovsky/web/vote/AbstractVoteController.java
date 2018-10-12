package uv.sokolovsky.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;
import uv.sokolovsky.service.RestaurantService;
import uv.sokolovsky.service.VoteService;
import uv.sokolovsky.to.RestaurantWithVotes;
import uv.sokolovsky.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

import static uv.sokolovsky.util.ValidationUtil.assureIdConsistent;
import static uv.sokolovsky.util.ValidationUtil.checkNew;

public class AbstractVoteController {
    private static final Logger log = LoggerFactory.getLogger(AbstractVoteController.class);

    @Autowired
    private VoteService voteService;

    @Autowired
    private RestaurantService restaurantService;

    public Vote create(Vote vote) {
        checkNew(vote);
        log.info("create vote user {} for restaurant {}", vote.getUser().getId(), vote.getRestaurant().getId());
        return voteService.create(vote);
    }

    public void update(Vote vote, int id) {
        assureIdConsistent(vote, id);
        log.info("update {} for restaurant {}", vote, vote.getRestaurant().getRestaurantName());
        voteService.update(vote);
    }

    public Vote getOneByDate(User user, LocalDate date) {
        log.info("getVoteByDate{} for user {}", date, user.getName());
        Vote vote = voteService.getOneByDate(user, date);
        return vote;
    }

    public List <RestaurantWithVotes> getVoteCount (LocalDate date) {
        log.info("getVoteCount by date {}", date);
        return RestaurantUtil.getRestaurantWithVotes(restaurantService.getAll(), voteService.getAllByDate(date));
    }
}
