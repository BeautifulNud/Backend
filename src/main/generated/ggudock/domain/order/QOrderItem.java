package ggudock.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderItem is a Querydsl query type for OrderItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderItem extends EntityPathBase<OrderItem> {

    private static final long serialVersionUID = -1599769347L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderItem orderItem = new QOrderItem("orderItem");

    public final ggudock.util.QBaseTimeEntity _super = new ggudock.util.QBaseTimeEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final QCustomerOrder customerOrder;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ggudock.domain.item.entity.QItem item;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public QOrderItem(String variable) {
        this(OrderItem.class, forVariable(variable), INITS);
    }

    public QOrderItem(Path<? extends OrderItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderItem(PathMetadata metadata, PathInits inits) {
        this(OrderItem.class, metadata, inits);
    }

    public QOrderItem(Class<? extends OrderItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerOrder = inits.isInitialized("customerOrder") ? new QCustomerOrder(forProperty("customerOrder"), inits.get("customerOrder")) : null;
        this.item = inits.isInitialized("item") ? new ggudock.domain.item.entity.QItem(forProperty("item"), inits.get("item")) : null;
    }

}

