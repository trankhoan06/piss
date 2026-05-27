package com.app.pis.repository;


import com.app.pis.entity.Unit;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    boolean existsByName(String name);


    @Query (value = "select unit from Unit unit")
    Stream<Unit> getAll ();


    @Override
    void deleteById(Integer integer);
}
