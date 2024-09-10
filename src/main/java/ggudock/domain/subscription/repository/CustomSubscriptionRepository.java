package ggudock.domain.subscription.repository;

import ggudock.domain.subscription.application.dto.DateResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomSubscriptionRepository {
    DateResponse findDates(Long subscriptionId);
}