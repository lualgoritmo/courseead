package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.repository.CourseUserRepository;
import com.luciano.ead.course.service.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {
    @Autowired
    private CourseUserRepository courseUserRepository;
}
