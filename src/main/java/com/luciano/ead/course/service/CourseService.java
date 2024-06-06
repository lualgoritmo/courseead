package com.luciano.ead.course.service;

import com.luciano.ead.course.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    void delete(Course course);

    Course save(Course course);

    Optional<Course> findById(UUID courseId);

    List<Course> findAll();
}
