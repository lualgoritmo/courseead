package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.CourseUser;
import com.luciano.ead.course.repository.CourseUserRepository;
import com.luciano.ead.course.service.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {
    @Autowired
    private CourseUserRepository courseUserRepository;

    public String createUrl(UUID userId, Pageable pageable) {
        return "/courses?userId=" + userId + "&page=" +
                pageable.getPageNumber() + "&size=" + pageable.getPageSize() +
                "&sort=" + pageable.getSort().toString().replaceAll(":", ",");
    }

    @Override
    public boolean existsByCourseAndUserId(Course courseId, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(courseId, userId);
    }

    @Override
    public CourseUser save(CourseUser courseUser) {
        return courseUserRepository.save(courseUser);
    }
}
