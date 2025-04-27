package com.djit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourse is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourse extends EntityPathBase<Course> {

    private static final long serialVersionUID = 1715081014L;

    public static final QCourse course = new QCourse("course");

    public final StringPath courseDescription = createString("courseDescription");

    public final StringPath courseName = createString("courseName");

    public final StringPath courseTagline = createString("courseTagline");

    public final StringPath duration = createString("duration");

    public final ListPath<String, StringPath> educationalGoals = this.<String, StringPath>createList("educationalGoals", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> roadmapMonth1 = this.<String, StringPath>createList("roadmapMonth1", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roadmapMonth2 = this.<String, StringPath>createList("roadmapMonth2", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roadmapMonth3 = this.<String, StringPath>createList("roadmapMonth3", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roadmapMonth4 = this.<String, StringPath>createList("roadmapMonth4", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roadmapMonth5 = this.<String, StringPath>createList("roadmapMonth5", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roadmapMonth6 = this.<String, StringPath>createList("roadmapMonth6", String.class, StringPath.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final ListPath<String, StringPath> subjects = this.<String, StringPath>createList("subjects", String.class, StringPath.class, PathInits.DIRECT2);

    public QCourse(String variable) {
        super(Course.class, forVariable(variable));
    }

    public QCourse(Path<? extends Course> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourse(PathMetadata metadata) {
        super(Course.class, metadata);
    }

}

