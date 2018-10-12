package uv.sokolovsky.web.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uv.sokolovsky.AuthorizedUser;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;
import uv.sokolovsky.service.RestaurantService;
import uv.sokolovsky.service.UserService;
import uv.sokolovsky.to.RestaurantWithVotes;
import uv.sokolovsky.util.DateTimeUtil;
import uv.sokolovsky.util.exception.VoitingTimeException;

import java.net.URI;
import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController extends AbstractVoteController {
    static final String REST_URL = "/rest/votes";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVotes> getVotes () {
        return super.getVoteCount(DateTimeUtil.DEFAULT_DATE);
    }


    @PutMapping(value = "/restaurantId/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("restaurantId") int restaurantId) {
        if (DateTimeUtil.isVoitingTime(DateTimeUtil.DEFAULT_DATE)) {
            Restaurant restaurant = restaurantService.get(restaurantId);
            User user = userService.get(AuthorizedUser.id());
            LocalDate currentDate = DateTimeUtil.DEFAULT_DATE;
            Vote vote = new Vote(restaurant, user, currentDate);
            int voteId = super.getOneByDate(user, currentDate).getId();
            super.update(vote, voteId);
        } else {
            throw new VoitingTimeException();
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestParam("restaurantId") int restaurantId) {
        if (DateTimeUtil.isVoitingTime(DateTimeUtil.DEFAULT_DATE)) {
            Restaurant restaurant = restaurantService.get(restaurantId);
            User user = userService.get(AuthorizedUser.id());
            Vote created = super.create(new Vote(restaurant, user, DateTimeUtil.DEFAULT_DATE));
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{restaurantId}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else {
            throw new VoitingTimeException();
        }
    }

    @GetMapping(value = "/getVotesByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List <RestaurantWithVotes> getByDate(@RequestParam ("date") LocalDate date) {
        if (date == null) {
            return super.getVoteCount(DateTimeUtil.DEFAULT_DATE);
        }
        return super.getVoteCount(date);
    }
}
