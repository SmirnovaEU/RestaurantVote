package ru.topjava.restaurant.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.restaurant.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.id=:id ORDER BY v.date desc")
    List<Vote> getAll(int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id= ?1 AND v.user.id= ?2")
    Vote getWithUser(int id, int userId);

}
