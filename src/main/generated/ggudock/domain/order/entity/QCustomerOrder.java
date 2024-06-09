package ggudock.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerOrder is a Querydsl query type for CustomerOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerOrder extends EntityPathBase<CustomerOrder> {

    private static final long serialVersionUID = 1963198473L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerOrder customerOrder = new QCustomerOrder("customerOrder");

    public final ggudock.util.QBaseTimeEntity _super = new ggudock.util.QBaseTimeEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final ggudock.domain.item.entity.QItem item;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final DatePath<java.time.LocalDate> orderDate = createDate("orderDate", java.time.LocalDate.class);

    public final StringPath orderId = createString("orderId");

    public final EnumPath<ggudock.domain.order.model.OrderStatus> orderStatus = createEnum("orderStatus", ggudock.domain.order.model.OrderStatus.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final ggudock.domain.user.entity.QUser user;

    public QCustomerOrder(String variable) {
        this(CustomerOrder.class, forVariable(variable), INITS);
    }

    public QCustomerOrder(Path<? extends CustomerOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerOrder(PathMetadata metadata, PathInits inits) {
        this(CustomerOrder.class, metadata, inits);
    }

    public QCustomerOrder(Class<? extends CustomerOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new ggudock.domain.item.entity.QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new ggudock.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

