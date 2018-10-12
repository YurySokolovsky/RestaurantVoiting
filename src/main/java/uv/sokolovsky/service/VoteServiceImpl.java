package uv.sokolovsky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;
import uv.sokolovsky.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static uv.sokolovsky.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {
    private VoteRepository repository;

    @Autowired
    VoteServiceImpl (VoteRepository repository)  { this.repository = repository; }

    @Override
    public Vote create(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    @Override
    public Vote update(Vote vote) {
        return checkNotFoundWithId(repository.save(vote), vote.getId());
    }

    @Override
    public Vote getOneByDate(User user, LocalDate date) {
        return repository.getOneByDate(user, date);
    }

    @Override
    public List<Vote> getAllByDate(LocalDate date) {
        return repository.getAllByDate(date);
    }
}
