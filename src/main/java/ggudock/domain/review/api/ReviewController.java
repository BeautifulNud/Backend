package ggudock.domain.review.api;

import ggudock.config.oauth.utils.SecurityUtil;
import ggudock.domain.review.application.ReviewService;
import ggudock.domain.review.dto.ReviewRequest;
import ggudock.domain.review.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰", description = "리뷰 api")
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 저장", description = "정보를 받아 리뷰를 저장한다.")
    @PostMapping()
    public ResponseEntity<ReviewResponse> saveReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return new ResponseEntity<>(reviewService.saveReview(reviewRequest, SecurityUtil.getCurrentName()),
                HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 Id를 받아 리뷰를 삭제한다.")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Long> deleteReview(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.delete(reviewId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "유저의 전체 리뷰 삭제", description = "로그인중인 유저의 전체 리뷰를 삭제한다.")
    @DeleteMapping("/deleteAll-User")
    public ResponseEntity<?> deleteReviewList() {
        System.out.println(SecurityUtil.getCurrentName());
        reviewService.deleteList(SecurityUtil.getCurrentName());
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "리뷰 Id로 리뷰 정보 받아오기", description = "리뷰 Id를 통해 단일 리뷰의 정보를 받아온다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getDetail(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.getDetail(reviewId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "유저별 리뷰 리스트", description = "유저별 최신순 리뷰 리스트를 보여준다")
    @GetMapping("/search-user")
    public ResponseEntity<Page<ReviewResponse>> getListByUser(@RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(reviewService.getListByUser(page, SecurityUtil.getCurrentName()), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "최신 날짜순 리뷰 리스트", description = "아이템의 전체 리뷰를 최신 날짜순으로 보여준다.")
    @GetMapping("/search-orderByDate-Desc")
    public ResponseEntity<Page<ReviewResponse>> getListOrderByDateDESC(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByDateDesc(page, itemId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "오래된 날짜순 리뷰 리스트", description = "아이템의 전체 리뷰를 오래된 날짜순으로 보여준다.")
    @GetMapping("/search-orderByDate-Asc")
    public ResponseEntity<Page<ReviewResponse>> getListOrderByDateASC(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByDateAsc(page, itemId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "높은 별점 순 리뷰 리스트", description = "아이템의 전체 리뷰를 높은 별점순으로 보여준다.")
    @GetMapping("/search-orderByRating-Desc")
    public ResponseEntity<Page<ReviewResponse>> getListOrderByRatingDESC(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByRatingDesc(page, itemId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "낮은 별점 순 리뷰 리스트", description = "아이템의 전체 리뷰를 낮은 별점순으로 보여준다.")
    @GetMapping("/search-orderByRating-Asc")
    public ResponseEntity<Page<ReviewResponse>> getListOrderByRatingAsc(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByRatingAsc(page, itemId), HttpStatusCode.valueOf(200));
    }
}
