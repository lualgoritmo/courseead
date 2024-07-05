package com.luciano.ead.course.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {
    String createUrlgetAllUsersByCourse(UUID courseId, Pageable pageable);
}
