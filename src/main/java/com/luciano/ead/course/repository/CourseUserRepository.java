package com.luciano.ead.course.repository;

import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {
    Boolean existsByCourseAndUserId(Course courseId, UUID userId);
}
