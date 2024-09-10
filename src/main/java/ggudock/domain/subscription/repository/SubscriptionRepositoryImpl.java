package ggudock.domain.subscription.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ggudock.domain.subscription.application.dto.DateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static ggudock.domain.subscription.entity.QSubscription.subscription;
import static ggudock.domain.subscription.entity.QSubscriptionSchedule.*;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements CustomSubscriptionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public DateResponse findDates(Long subscriptionId) {
        List<LocalDate> dates = jpaQueryFactory
                .select(subscriptionSchedule.date)
                .from(subscriptionSchedule)
                .where(subscriptionSchedule.subscription.id.eq(subscriptionId)
                        .and(subscriptionSchedule.date.after(LocalDate.now())
                                .or(subscriptionSchedule.date.eq(LocalDate.now()))))
                .fetch();
        String itemName = jpaQueryFactory
                .select(subscription.order.item.name)
                .from(subscription)
                .where(subscription.id.eq(subscriptionId))
                .fetchOne();
        return DateResponse.builder()
                .itemName(itemName)
                .subscriptionId(subscriptionId)
                .dates(dates)
                .build();
    }
}
