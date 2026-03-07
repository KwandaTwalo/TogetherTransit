package com.kwndtwalo.TogetherTransit.service.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.repository.school.ISchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService implements ISchoolService {

    private ISchoolRepository schoolRepository;

    @Autowired
    public SchoolService(ISchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public School create(School school) {
        if (school == null) {
            return null;
        }
        return schoolRepository
                .findBySchoolName(school.getSchoolName())
                .orElseGet(() -> schoolRepository.save(school));
    }

    @Override
    public School read(Long Id) {
        return schoolRepository.findById(Id).orElse(null);
    }

    @Override
    public School update(School school) {
        if (school == null || school.getSchoolId() == null) {
            return null;
        }
        if (!schoolRepository.existsById(school.getSchoolId())) {
            return null;
        }
        return schoolRepository.save(school);
    }

    @Override
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public boolean delete(Long Id) {
        if (!schoolRepository.existsById(Id)) {
            return false;
        }
        schoolRepository.deleteById(Id);
        return true;
    }
}
