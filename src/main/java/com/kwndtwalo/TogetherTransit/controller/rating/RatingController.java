package com.kwndtwalo.TogetherTransit.controller.rating;

import com.kwndtwalo.TogetherTransit.domain.rating.Rating;
import com.kwndtwalo.TogetherTransit.dto.rating.RatingDTO;
import com.kwndtwalo.TogetherTransit.service.rating.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * CREATE RATING
     * -------------------------------------------------------
     * This endpoint allows a parent to submit a rating for a driver
     * after completing a booking.
     *
     * Business Rules:
     * - A booking can ONLY be rated once.
     * - If the booking has already been rated, the system
     *   returns the existing rating instead of creating a duplicate.
     * - New ratings normally start with status = PENDING_REVIEW
     *   so that admins can moderate them before they become public.
     *
     * Why we return RatingDTO:
     * - Prevents exposing full domain objects
     * - Avoids circular relationships between Parent, Driver, Booking
     */
    @PostMapping("/create")
    public ResponseEntity<RatingDTO> create(@RequestBody Rating rating) {

        Rating createdRating = ratingService.create(rating);

        if (createdRating == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new RatingDTO(createdRating));
    }

    @GetMapping("/read/{Id}")
    public ResponseEntity<Rating> read(@PathVariable Long Id) {

        Rating readRating = ratingService.read(Id);
        if (readRating == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(readRating);
    }

    @PutMapping("/update")
    public ResponseEntity<Rating> update(@RequestBody Rating rating) {

        Rating updatedRating = ratingService.update(rating);
        if (updatedRating == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedRating);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RatingDTO>> getAllRatings() {

        List<RatingDTO> ratings = ratingService.getAllRatings()
                .stream()
                .map(RatingDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ratings);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {

        boolean deleted = ratingService.delete(id);

        return ResponseEntity.ok(deleted);
    }
}
