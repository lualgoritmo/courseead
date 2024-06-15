package com.luciano.ead.course.specification;

import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.CourseUser;
import com.luciano.ead.course.model.Lesson;
import com.luciano.ead.course.model.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {
    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<Course> {
    }

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<ModuleModel> {
    }

    @Spec(path = "title", spec = Equal.class)
    public interface LessonSpec extends Specification<Lesson> {
    }

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<ModuleModel> module = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules");

            return cb.and(cb.equal(course.get("courseId"), courseId),
                    cb.isMember(module, coursesModules));
        };
    }

    public static Specification<Lesson> lessonModuleId(final UUID moduleId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Lesson> lesson = root;
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");

            return cb.and(cb.equal(module.get("moduleId"), moduleId),
                    cb.isMember(lesson, moduleLessons));
        };
    }

    public static Specification<Course> courseUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Course, CourseUser> courseProd = root.join("coursesUsers");
            return cb.equal(courseProd.get("userId"), userId);
        };
    }
}
