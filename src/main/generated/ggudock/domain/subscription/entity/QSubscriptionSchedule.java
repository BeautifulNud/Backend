package ggudock.domain.subscription.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubscriptionSchedule is a Querydsl query type for SubscriptionSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubscriptionSchedule extends EntityPathBase<SubscriptionSchedule> {

    private static final long serialVersionUID = -1910762990L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubscriptionSchedule subscriptionSchedule = new QSubscriptionSchedule("subscriptionSchedule");

    public final ggudock.util.QBaseTimeEntity _super = new ggudock.util.QBaseTimeEntity(this);

    public final ggudock.domain.address.entity.QAddress address;

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final EnumPath<java.time.DayOfWeek> day = createEnum("day", java.time.DayOfWeek.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final EnumPath<ggudock.domain.subscription.model.ScheduleState> scheduleState = createEnum("scheduleState", ggudock.domain.subscription.model.ScheduleState.class);

    public final QSubscription subscription;

    public QSubscriptionSchedule(String variable) {
        this(SubscriptionSchedule.class, forVariable(variable), INITS);
    }

    public QSubscriptionSchedule(Path<? extends SubscriptionSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubscriptionSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubscriptionSchedule(PathMetadata metadata, PathInits inits) {
        this(SubscriptionSchedule.class, metadata, inits);
    }

    public QSubscriptionSchedule(Class<? extends SubscriptionSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new ggudock.domain.address.entity.QAddress(forProperty("address"), inits.get("address")) : null;
        this.subscription = inits.isInitialized("subscription") ? new QSubscription(forProperty("subscription"), inits.get("subscription")) : null;
    }

}

