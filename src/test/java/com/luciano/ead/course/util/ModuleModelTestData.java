package com.luciano.ead.course.util;

import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.ModuleModel;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

public class ModuleModelTestData {
    public static ModuleModel moduleModel1() {
        ModuleModel module = new ModuleModel();
        module.setModuleId(UUID.randomUUID());
        module.setTitle("Module 1");
        module.setDescription("Description of Module 1");
        module.setCreationDate(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        module.setCourse(course());
        module.setLessons(new HashSet<>());
        return module;
    }

    public static ModuleModel moduleModel2() {
        ModuleModel module = new ModuleModel();
        module.setModuleId(UUID.randomUUID());
        module.setTitle("Module 2");
        module.setDescription("Description of Module 2");
        module.setCreationDate(LocalDateTime.of(2023, 2, 1, 12, 0, 0));
        module.setCourse(course());
        module.setLessons(new HashSet<>());
        return module;
    }

    public static Course course() {
        Course course = new Course();
        course.setCourseId(UUID.randomUUID());
        course.setDescription("Description of Sample Course");
        course.setCreationDate(LocalDateTime.of(2022, 1, 1, 12, 0, 0));
        return course;
    }

}
