package com.luciano.ead.course.controller;

import com.luciano.ead.course.service.LessonService;
import com.luciano.ead.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/modules")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @Autowired
    private LessonService lessonService;



}
