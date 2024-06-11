package ggudock.domain.review.application;

import ggudock.domain.picture.application.S3UploadService;
import ggudock.domain.review.entity.Review;
import ggudock.domain.review.entity.ReviewImage;
import ggudock.domain.review.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewImageService  {

    private final ReviewImageRepository reviewImageRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void save(MultipartFile image, Review review) throws IOException {
        String fileUrl = s3UploadService.upload(image, "images");
        ReviewImage reviewImage = new ReviewImage(fileUrl, review);
        reviewImageRepository.save(reviewImage);
    }

    @Transactional
    public void delete(Long pictuerId) {
        reviewImageRepository.deleteById(pictuerId);
    }

    @Transactional
    public void deleteAll(Long reviewId) {
        reviewImageRepository.deleteAllByReview_Id(reviewId);
    }

    public List<ReviewImage> findImages(Long reviewId) {
        return reviewImageRepository.findAllByReview_Id(reviewId);
    }
}
