package ggudock.domain.subscription.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static ggudock.domain.item.entity.QItem.*;
import static ggudock.domain.subscription.entity.QSubscription.subscription;
import static ggudock.domain.subscription.entity.QSubscriptionSchedule.*;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements CustomSubscriptionRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Integer createPrice(Long subscriptionId) {
        return jpaQueryFactory
                .select(item.price.multiply(subscriptionSchedule.count()))
                .from(subscription)
                .join(subscriptionSchedule).on(subscription.eq(subscriptionSchedule.subscription))
                .join(item).on(subscription.item.eq(item))
                .where(subscription.id.eq(subscriptionId))
                .fetchOne();
    }
}