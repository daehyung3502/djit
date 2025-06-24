package com.djit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApplication is a Querydsl query type for Application
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApplication extends EntityPathBase<Application> {

    private static final long serialVersionUID = -907735275L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApplication application = new QApplication("application");

    public final StringPath address = createString("address");

    public final StringPath birth = createString("birth");

    public final QConsultation consultation;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath educationGoal = createString("educationGoal");

    public final StringPath educationLevel = createString("educationLevel");

    public final StringPath email = createString("email");

    public final StringPath employmentInsurance = createString("employmentInsurance");

    public final StringPath married = createString("married");

    public final StringPath motivation = createString("motivation");

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final NumberPath<Long> number = createNumber("number", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath sex = createString("sex");

    public final StringPath subjectName = createString("subjectName");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QApplication(String variable) {
        this(Application.class, forVariable(variable), INITS);
    }

    public QApplication(Path<? extends Application> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApplication(PathMetadata metadata, PathInits inits) {
        this(Application.class, metadata, inits);
    }

    public QApplication(Class<? extends Application> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consultation = inits.isInitialized("consultation") ? new QConsultation(forProperty("consultation"), inits.get("consultation")) : null;
    }

}

