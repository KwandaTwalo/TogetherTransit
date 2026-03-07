package com.kwndtwalo.TogetherTransit.repository.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findBySchoolName(String schoolName);
}
