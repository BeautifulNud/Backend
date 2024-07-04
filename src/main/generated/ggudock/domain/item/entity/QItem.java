package ggudock.domain.item.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 250628935L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final ggudock.util.QBaseTimeEntity _super = new ggudock.util.QBaseTimeEntity(this);

    public final ListPath<ggudock.domain.cart.entity.Cart, ggudock.domain.cart.entity.QCart> cartList = this.<ggudock.domain.cart.entity.Cart, ggudock.domain.cart.entity.QCart>createList("cartList", ggudock.domain.cart.entity.Cart.class, ggudock.domain.cart.entity.QCart.class, PathInits.DIRECT2);

    public final ggudock.domain.category.entity.QCategory category;

    public final ggudock.domain.company.entity.QCompany company;

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ItemImage, QItemImage> itemImageList = this.<ItemImage, QItemImage>createList("itemImageList", ItemImage.class, QItemImage.class, PathInits.DIRECT2);

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath plan = createString("plan");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    public final ListPath<ggudock.domain.review.entity.Review, ggudock.domain.review.entity.QReview> reviewList = this.<ggudock.domain.review.entity.Review, ggudock.domain.review.entity.QReview>createList("reviewList", ggudock.domain.review.entity.Review.class, ggudock.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final NumberPath<Integer> salePercent = createNumber("salePercent", Integer.class);

    public final StringPath thumbnail = createString("thumbnail");

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new ggudock.domain.category.entity.QCategory(forProperty("category")) : null;
        this.company = inits.isInitialized("company") ? new ggudock.domain.company.entity.QCompany(forProperty("company")) : null;
    }

}

