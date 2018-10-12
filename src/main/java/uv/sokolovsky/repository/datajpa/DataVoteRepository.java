package uv.sokolovsky.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;
import uv.sokolovsky.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataVoteRepository implements VoteRepository {
    @Autowired
    private CrudVoteRepository crudRepository;

    @Override
    @Transactional
    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }

    @Override
    public Vote getOneByDate (User user, LocalDate date) {
        return crudRepository.getOneByDate(user, date);
    };

    @Override
    public List<Vote> getAllByDate (LocalDate date) {
        return crudRepository.getAllByDate(date);
    }
}
