package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.repository.LessonRepository;
import com.luciano.ead.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
}
