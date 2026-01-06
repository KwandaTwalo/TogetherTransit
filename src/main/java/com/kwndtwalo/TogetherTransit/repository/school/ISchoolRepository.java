package com.kwndtwalo.TogetherTransit.repository.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISchoolRepository extends JpaRepository<School, Long> {

    List<School> findBySchoolName(String schoolName);
}
