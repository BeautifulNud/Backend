package ggudock.domain.review.api;

import ggudock.domain.review.application.ReviewImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 사진", description = "리뷰 사진 api")
@RequestMapping("/api/reviewImage")
public class ReviewImageController {
    private final ReviewImageService reviewImageService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteReviewImage(@PathVariable("id") Long id){
        reviewImageService.delete(id);
        return new ResponseEntity<>(id , HttpStatusCode.valueOf(200));
    }
}