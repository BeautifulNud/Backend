package ggudock.domain.review.application;

import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.picture.application.S3UploadService;
import ggudock.domain.review.dto.ReviewRequest;
import ggudock.domain.review.dto.ReviewResponse;
import ggudock.domain.review.entity.Review;
import ggudock.domain.review.entity.ReviewImage;
import ggudock.domain.review.repository.ReviewRepository;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final S3UploadService s3UploadService;
    private final ReviewImageService reviewImageService;

    @Transactional
    public ReviewResponse saveReview(ReviewRequest reviewRequest, String email, List<MultipartFile> image) throws IOException {
        User user = getUser(email);
        Item item = getItem(reviewRequest.getItemId());
        Review review = createReview(reviewRequest, item, user);
        Review savedReview = reviewRepository.save(review);

        if (!image.isEmpty()) {
            for (MultipartFile multipartFile : image)
                reviewImageService.save(multipartFile, review);
        }

        return getDetail(savedReview.getId());
    }

    @Transactional
    public Long delete(Long reviewId, String email) {
        if (checkLoginEmial(reviewId, email)) {
            List<ReviewImage> images = reviewImageService.findImages(reviewId);

            if (!images.isEmpty()) {
                for (ReviewImage image : images)
                    s3UploadService.fileDelete(image.getImageUrl());
            }

            reviewImageService.deleteAll(reviewId);
            reviewRepository.deleteById(reviewId);
        }
        return reviewId;
    }

    @Transactional
    public void deleteList(String email) {
        List<Review> reviewList = reviewRepository.findByUser_EmailOrderByDateDesc(email);
        for (Review review : reviewList) {
            List<ReviewImage> images = reviewImageService.findImages(review.getId());

            if (!images.isEmpty()) {
                for (ReviewImage image : images)
                    s3UploadService.fileDelete(image.getImageUrl());
            }
            reviewImageService.deleteAll(review.getId());
        }

        reviewRepository.deleteAll(reviewList);
    }

    public Page<ReviewResponse> getListByUser(int page, String email) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Review> reviewPage = createReviewPageByUser(pageRequest, email);
        return createReviewResponsePage(reviewPage);
    }

    public Page<ReviewResponse> getListOrderByDateAsc(int page, Long itemId) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Review> reviewPage = createReviewPageByItemOrderByDateAsc(pageRequest, itemId);
        return createReviewResponsePage(reviewPage);
    }

    public Page<ReviewResponse> getListOrderByDateDesc(int page, Long itemId) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Review> reviewPage = createReviewPageByItemOrderByDateDesc(pageRequest, itemId);
        return createReviewResponsePage(reviewPage);
    }

    public Page<ReviewResponse> getListOrderByRatingAsc(int page, Long itemId) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Review> reviewPage = createReviewPageByItemOrderByRatingAsc(pageRequest, itemId);
        return createReviewResponsePage(reviewPage);
    }

    public Page<ReviewResponse> getListOrderByRatingDesc(int page, Long itemId) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Review> reviewPage = createReviewPageByItemOrderByRatingDesc(pageRequest, itemId);
        return createReviewResponsePage(reviewPage);
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

    private static ReviewResponse createReviewResposnse(Review review) {
        return new ReviewResponse(review);
    }

    private static PageRequest createPageRequest(int page) {
        return PageRequest.of(page, 10);
    }

    private static Page<ReviewResponse> createReviewResponsePage(Page<Review> reviewPage) {
        return reviewPage.map(ReviewResponse::new);
    }

    private Page<Review> createReviewPageByUser(PageRequest pageRequest, String email) {
        return reviewRepository.findByUser_EmailOrderByDateDesc(email, pageRequest);
    }

    private Page<Review> createReviewPageByItemOrderByDateAsc(PageRequest pageRequest, Long itemId) {
        return reviewRepository.findByItem_IdOrderByDateAsc(itemId, pageRequest);
    }

    private Page<Review> createReviewPageByItemOrderByDateDesc(PageRequest pageRequest, Long itemId) {
        return reviewRepository.findByItem_IdOrderByDateDesc(itemId, pageRequest);
    }

    private Page<Review> createReviewPageByItemOrderByRatingAsc(PageRequest pageRequest, Long itemId) {
        return reviewRepository.findByItem_IdOrderByRatingAscDateDesc(itemId, pageRequest);
    }

    private Page<Review> createReviewPageByItemOrderByRatingDesc(PageRequest pageRequest, Long itemId) {
        return reviewRepository.findByItem_IdOrderByRatingDescDateDesc(itemId, pageRequest);
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

    private boolean checkLoginEmial(Long reviewId, String email) {
        User login = userRepository.findByEmail(email);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));
        return login.getEmail().equals(review.getUser().getEmail());
    }

}
