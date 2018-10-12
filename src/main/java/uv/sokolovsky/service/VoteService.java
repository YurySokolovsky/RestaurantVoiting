package uv.sokolovsky.service;

import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote create(Vote vote);

    Vote update(Vote vote);

    Vote getOneByDate(User user, LocalDate date);

    List<Vote> getAllByDate(LocalDate date);
}
