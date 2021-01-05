package ru.topjava.restaurant.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.restaurant.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.rest.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT d FROM Dish d WHERE d.rest.id=:restId AND d.date=:date ORDER BY d.name DESC")
    List<Dish> getAllByDate(@Param("restId") int restId, @Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d JOIN FETCH d.rest WHERE d.id = ?1 AND d.rest.id = ?2")
    Dish getWithRest(int id, int restId);
}
