package ggudock.domain.subscription.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubscription is a Querydsl query type for Subscription
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubscription extends EntityPathBase<Subscription> {

    private static final long serialVersionUID = 2089657947L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubscription subscription = new QSubscription("subscription");

    public final ggudock.util.QBaseTimeEntity _super = new ggudock.util.QBaseTimeEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ggudock.domain.item.entity.QItem item;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> period = createNumber("period", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> subscribeDate = createDateTime("subscribeDate", java.time.LocalDateTime.class);

    public final ggudock.domain.user.entity.QUser user;

    public QSubscription(String variable) {
        this(Subscription.class, forVariable(variable), INITS);
    }

    public QSubscription(Path<? extends Subscription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubscription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubscription(PathMetadata metadata, PathInits inits) {
        this(Subscription.class, metadata, inits);
    }

    public QSubscription(Class<? extends Subscription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new ggudock.domain.item.entity.QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new ggudock.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

