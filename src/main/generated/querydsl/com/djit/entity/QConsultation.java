package com.djit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConsultation is a Querydsl query type for Consultation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsultation extends EntityPathBase<Consultation> {

    private static final long serialVersionUID = -325910172L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConsultation consultation = new QConsultation("consultation");

    public final QApplication application;

    public final DateTimePath<java.time.LocalDateTime> consultationDateTime = createDateTime("consultationDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> number = createNumber("number", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QConsultation(String variable) {
        this(Consultation.class, forVariable(variable), INITS);
    }

    public QConsultation(Path<? extends Consultation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConsultation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConsultation(PathMetadata metadata, PathInits inits) {
        this(Consultation.class, metadata, inits);
    }

    public QConsultation(Class<? extends Consultation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.application = inits.isInitialized("application") ? new QApplication(forProperty("application"), inits.get("application")) : null;
    }

}

