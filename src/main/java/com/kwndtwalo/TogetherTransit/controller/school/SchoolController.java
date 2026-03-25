package com.kwndtwalo.TogetherTransit.controller.school;

import com.kwndtwalo.TogetherTransit.domain.school.School;
import com.kwndtwalo.TogetherTransit.dto.school.SchoolDTO;
import com.kwndtwalo.TogetherTransit.service.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/create")
    public ResponseEntity<SchoolDTO> create(@RequestBody School school) {

        School savedSchool = schoolService.create(school);

        if (savedSchool == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new SchoolDTO(savedSchool));
    }

    @GetMapping("/read/{Id}")
    public ResponseEntity<School> read(@PathVariable Long Id) {

        School school = schoolService.read(Id);
        if (school == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(school);
    }

    @PutMapping("/update")
    public ResponseEntity<School> update(@RequestBody School school) {

        School updatedSchool = schoolService.update(school);

        if (updatedSchool == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSchool);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.ok(schoolService.getAllSchools());
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long Id) {
        boolean deleted = schoolService.delete(Id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }

}
