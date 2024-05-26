package ggudock.domain.review.api;

import ggudock.config.oauth.utils.SecurityUtil;
import ggudock.domain.review.application.ReviewService;
import ggudock.domain.review.dto.ReviewRequest;
import ggudock.domain.review.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @DeleteMapping("/deleteListByUser")
    public ResponseEntity<Long> deleteReviewList() {
        System.out.println(SecurityUtil.getCurrentName());
        reviewService.deleteList(SecurityUtil.getCurrentName());
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "단일 리뷰 받아오기", description = "유저별 최신순 리뷰 리스트를 보여준다")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.getDetail(reviewId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "유저별 리뷰 리스트", description = "유저별 최신순 리뷰 리스트를 보여준다")
    @GetMapping("/listByUser")
    public ResponseEntity<List<ReviewResponse>> getListByUser() {
        return new ResponseEntity<>(reviewService.getListByUser(SecurityUtil.getCurrentName()), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "최신 날짜순 리뷰 리스트", description = "아이템의 전체 리뷰를 최신 날짜순으로 보여준다.")
    @GetMapping("/listOrderByDate_Desc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByDateDESC(@RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByDateDesc(itemId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "오래된 날짜순 리뷰 리스트", description = "아이템의 전체 리뷰를 오래된 날짜순으로 보여준다.")
    @GetMapping("/listOrderByDate_Asc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByDateASC(@RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByDateAsc(itemId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "높은 별점 순 리뷰 리스트", description = "아이템의 전체 리뷰를 높은 별점순으로 보여준다.")
    @GetMapping("/listOrderByRating_Desc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByRatingDESC(@RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByRatingDesc(itemId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "낮은 별점 순 리뷰 리스트", description = "아이템의 전체 리뷰를 낮은 별점순으로 보여준다.")
    @GetMapping("/listOrderByRating-Asc")
    public ResponseEntity<List<ReviewResponse>> getListOrderByRatingAsc(@RequestParam("itemId") Long itemId) {
        return new ResponseEntity<>(reviewService.getListOrderByRatingAsc(itemId), HttpStatusCode.valueOf(200));
    }
}
