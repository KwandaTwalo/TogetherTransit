package com.kwndtwalo.TogetherTransit.controller.child;

import com.kwndtwalo.TogetherTransit.domain.child.Child;
import com.kwndtwalo.TogetherTransit.dto.child.ChildDTO;
import com.kwndtwalo.TogetherTransit.service.child.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/child")
public class ChildController {

    private final ChildService childService;

    @Autowired
    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping("/create")
    public ResponseEntity<ChildDTO> create(@RequestBody Child child) {

        Child created = childService.create(child);
         if (created == null) {
             return ResponseEntity.badRequest().build();
         }
         return ResponseEntity.ok(new ChildDTO(created));
    }

    @GetMapping("/read/{Id}")
    public ResponseEntity<ChildDTO> read(@PathVariable Long Id) {

        Child read = childService.read(Id);
        if (read == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ChildDTO(read));
    }

    @PutMapping("/update")
    public ResponseEntity<ChildDTO> update(@RequestBody Child child) {
        Child updated = childService.update(child);
        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ChildDTO(updated));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ChildDTO>> getAll() {

        List<ChildDTO> childList = childService.getAllChildren()
                .stream()
                .map(ChildDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(childList);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long Id) {
        boolean deleted = childService.delete(Id);
        return ResponseEntity.ok(deleted);
    }
}
