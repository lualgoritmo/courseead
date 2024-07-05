package com.luciano.ead.course.service;

import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.CourseUser;

import java.util.UUID;

public interface CourseUserService {
    boolean existsByCourseAndUserId(Course courseId, UUID userId);

    CourseUser save(CourseUser courseUser);
}
