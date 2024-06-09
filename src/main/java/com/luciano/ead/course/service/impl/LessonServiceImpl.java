package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.model.Lesson;
import com.luciano.ead.course.repository.LessonRepository;
import com.luciano.ead.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public Lesson save(Lesson lesson) { return lessonRepository.save(lesson); }

    @Override
    public void delete(Lesson lesson) { lessonRepository.delete(lesson);}

    @Override
    public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    @Override
    public List<Lesson> findByAllLessons(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }
}
