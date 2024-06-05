package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.repository.ModuleRepository;
import com.luciano.ead.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
}
