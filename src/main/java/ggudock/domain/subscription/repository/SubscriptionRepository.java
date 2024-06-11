package ggudock.domain.subscription.repository;

import ggudock.domain.subscription.entity.Subscription;
import ggudock.domain.subscription.model.State;
import ggudock.domain.subscription.model.SubType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long>,CustomSubscriptionRepository {
    Page<Subscription> findAllByUser_Email(String email,Pageable pageable);
    Page<Subscription> findAllByStateAndUser_Email(State state,String email, Pageable pageable);
    Page<Subscription> findAllBySubTypeAndUser_Email(SubType subType,String email,Pageable pageable);
    Page<Subscription> findAllByTitleAndUser_Email(String title,String email,Pageable pageable);
}