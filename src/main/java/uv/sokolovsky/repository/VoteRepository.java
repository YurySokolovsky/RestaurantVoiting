package uv.sokolovsky.repository;

import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if updated vote do not belong to user
    Vote save(Vote vote);

    Vote getOneByDate (User user, LocalDate date);

    List<Vote> getAllByDate (LocalDate date);
}

