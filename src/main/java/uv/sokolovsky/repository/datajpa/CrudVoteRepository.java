package uv.sokolovsky.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uv.sokolovsky.model.User;
import uv.sokolovsky.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Override
    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v WHERE v.user=:user AND v.date=:date")
    Vote getOneByDate (@Param("user")User user, @Param("date") LocalDate date);

    @Query("SELECT v from Vote v WHERE v.date=:date")
    List<Vote> getAllByDate(@Param("date") LocalDate date);
}
