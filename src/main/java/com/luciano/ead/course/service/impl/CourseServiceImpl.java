package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.Lesson;
import com.luciano.ead.course.model.Module;
import com.luciano.ead.course.repository.CourseRepository;
import com.luciano.ead.course.repository.LessonRepository;
import com.luciano.ead.course.repository.ModuleRepository;
import com.luciano.ead.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(Course course) {
        List<Module> moduleList = moduleRepository.findAllModulesIntoCourse(course.getCourseId());

        if (!moduleList.isEmpty()) {
            for (Module module : moduleList) {
                List<Lesson> lessonList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonList.isEmpty()) {
                    lessonRepository.deleteAll(lessonList);
                }
            }
            moduleRepository.deleteAll(moduleList);
        }
        courseRepository.delete(course);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
