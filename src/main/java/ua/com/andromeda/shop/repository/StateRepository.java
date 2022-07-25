package ua.com.andromeda.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ua.com.andromeda.shop.entity.State;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    List<State> findByCountryCode(@Param("code") String code);
}
