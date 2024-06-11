package ggudock.domain.company.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyImage is a Querydsl query type for CompanyImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyImage extends EntityPathBase<CompanyImage> {

    private static final long serialVersionUID = -981609868L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyImage companyImage = new QCompanyImage("companyImage");

    public final ggudock.util.QBaseTimeEntity _super = new ggudock.util.QBaseTimeEntity(this);

    public final QCompany company;

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final StringPath picture = createString("picture");

    public QCompanyImage(String variable) {
        this(CompanyImage.class, forVariable(variable), INITS);
    }

    public QCompanyImage(Path<? extends CompanyImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyImage(PathMetadata metadata, PathInits inits) {
        this(CompanyImage.class, metadata, inits);
    }

    public QCompanyImage(Class<? extends CompanyImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

