package uv.sokolovsky.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uv.sokolovsky.AuthorizedUser;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;
import uv.sokolovsky.service.RestaurantService;
import uv.sokolovsky.service.UserService;
import uv.sokolovsky.to.RestaurantWithVotes;
import uv.sokolovsky.util.DateTimeUtil;
import uv.sokolovsky.util.exception.VoitingTimeException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ajax/votes")
public class VoteAjaxController extends AbstractVoteController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVotes> getVotes () {
        return super.getVoteCount(DateTimeUtil.DEFAULT_DATE);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List <RestaurantWithVotes> vote(@RequestParam (value="date", required = false) LocalDate date,
                                           @RequestParam (value="restaurantId", required = false) Integer restaurantId) throws Exception{
        Restaurant restaurant = restaurantService.get(restaurantId);
        User user = userService.get(AuthorizedUser.id());
        LocalDate currentDate = DateTimeUtil.DEFAULT_DATE;
        Vote vote = new Vote(restaurant, user, currentDate);
        if (super.getOneByDate(user, currentDate) != null) {
            if (DateTimeUtil.isVoitingTime(date)) {
                int voteId = super.getOneByDate(user, currentDate).getId();
                super.update(vote, voteId);
            } else {
                throw new VoitingTimeException();
            }
        } else {
            super.create(vote);
        }
        return super.getVoteCount(currentDate);
    }

    @PostMapping(value = "/getVotesByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List <RestaurantWithVotes> getByDate(@RequestParam (value="date", required = false) LocalDate date) {
        if (date == null) {
            return super.getVoteCount(DateTimeUtil.DEFAULT_DATE);
        }
        return super.getVoteCount(date);
    }
}
