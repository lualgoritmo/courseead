package com.luciano.ead.course.service;

import com.luciano.ead.course.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    Lesson save(Lesson lesson);

    void delete(Lesson lesson);

    Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId);

    List<Lesson> findByAllLessons(UUID lessonId);

    Page<Lesson> findByAllLessons(Specification<Lesson> spec, Pageable pageable);
}
