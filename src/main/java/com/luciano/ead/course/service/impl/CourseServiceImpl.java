package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.repository.CourseRepository;
import com.luciano.ead.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
}
