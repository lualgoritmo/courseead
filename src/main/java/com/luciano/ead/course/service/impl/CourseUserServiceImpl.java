package com.luciano.ead.course.service.impl;

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

    String REQUEST_URI = "http://localhost:8087/";
    public String createUrl(UUID userId, Pageable pageable) {
        return REQUEST_URI + "/courses?userId=" + userId + "&page=" +
                pageable.getPageNumber() + "&size=" + pageable.getPageSize() +
                "&sort=" + pageable.getSort().toString().replaceAll(":", ",");
    }
}
