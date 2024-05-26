package ggudock.domain.review.application;

import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.review.dto.ReviewRequest;
import ggudock.domain.review.dto.ReviewResponse;
import ggudock.domain.review.entity.Review;
import ggudock.domain.review.repository.ReviewRepository;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponse saveReview(ReviewRequest reviewRequest, String email) {
        User user = getUser(email);
        Item item = getItem(reviewRequest.getItemId());
        Review review = createReview(reviewRequest, item, user);
        Review savedReview = reviewRepository.save(review);

        return getDetail(savedReview.getId());
    }

    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public Long delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return reviewId;
    }

    @Transactional
    public void deleteList(String email) {
        List<Review> reviewList = reviewRepository.findByUser_EmailOrderByDateDesc(email);
        reviewRepository.deleteAll(reviewList);
    }

    public List<ReviewResponse> getListOrderByDateDesc(Long itemId) {
        return reviewRepository.findByItem_IdOrderByDateDesc(itemId).stream()
                .map(Review::getId)
                .map(this::getDetail)
                .toList();
    }

    public List<ReviewResponse> getListOrderByDateAsc(Long itemId) {
        return reviewRepository.findByItem_IdOrderByDateAsc(itemId).stream()
                .map(Review::getId)
                .map(this::getDetail)
                .toList();
    }

    public List<ReviewResponse> getListOrderByRatingDesc(Long itemId) {
        return reviewRepository.findByItem_IdOrderByRatingDescDateDesc(itemId).stream()
                .map(Review::getId)
                .map(this::getDetail)
                .toList();
    }

    public List<ReviewResponse> getListOrderByRatingAsc(Long itemId) {
        return reviewRepository.findByItem_IdOrderByRatingAscDateDesc(itemId).stream()
                .map(Review::getId)
                .map(this::getDetail)
                .toList();
    }

    public List<ReviewResponse> getListByUser(String email) {
        return reviewRepository.findByUser_EmailOrderByDateDesc(email).stream()
                .map(Review::getId)
                .map(this::getDetail)
                .toList();
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));
    }

    @Transactional(readOnly = true)
    public ReviewResponse getDetail(Long reviewId) {
        Review review = getReview(reviewId);
        return createReviewResposnse(review);
    }

    @Transactional
    public Review createReview(ReviewRequest reviewRequest, Item item, User user) {
        return Review.builder()
                .content(reviewRequest.getContent())
                .rating(reviewRequest.getRating())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .item(item)
                .user(user)
                .build();
    }

    private static ReviewResponse createReviewResposnse(Review review) {
        return new ReviewResponse(review);
    }
}
