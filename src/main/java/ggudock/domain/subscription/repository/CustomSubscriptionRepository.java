package ggudock.domain.subscription.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomSubscriptionRepository {
    Integer createPrice(Long subscriptionId);
}