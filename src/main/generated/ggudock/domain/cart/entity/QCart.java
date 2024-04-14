package ggudock.domain.cart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = 403808225L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCart cart = new QCart("cart");

    public final ggudock.util.QBaseTimeEntity _super = new ggudock.util.QBaseTimeEntity(this);

    public final EnumPath<ggudock.domain.cart.model.Category> category = createEnum("category", ggudock.domain.cart.model.Category.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ggudock.domain.item.entity.QItem item;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final ggudock.domain.user.entity.QUser user;

    public QCart(String variable) {
        this(Cart.class, forVariable(variable), INITS);
    }

    public QCart(Path<? extends Cart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCart(PathMetadata metadata, PathInits inits) {
        this(Cart.class, metadata, inits);
    }

    public QCart(Class<? extends Cart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new ggudock.domain.item.entity.QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new ggudock.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

