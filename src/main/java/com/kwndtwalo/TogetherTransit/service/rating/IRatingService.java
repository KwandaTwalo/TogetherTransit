package com.kwndtwalo.TogetherTransit.service.rating;

import com.kwndtwalo.TogetherTransit.domain.rating.Rating;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IRatingService extends IService<Rating, Long> {
    List<Rating> getAllRatings();
}
