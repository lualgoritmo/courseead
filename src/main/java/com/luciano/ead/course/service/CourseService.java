package com.luciano.ead.course.service;

import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.specification.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    void delete(Course course);

    Course save(Course course);

    Optional<Course> findById(UUID courseId);

    List<Course> findAll();

    Page<Course> findAll(Specification<Course> spec, Pageable pageable);
}
