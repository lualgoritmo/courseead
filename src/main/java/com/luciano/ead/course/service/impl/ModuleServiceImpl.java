package com.luciano.ead.course.service.impl;

import com.luciano.ead.course.model.Lesson;
import com.luciano.ead.course.model.Module;
import com.luciano.ead.course.repository.LessonRepository;
import com.luciano.ead.course.repository.ModuleRepository;
import com.luciano.ead.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(Module module) {
        List<Lesson> lessonList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
        if (!lessonList.isEmpty()) {
            lessonRepository.deleteAll(lessonList);
        }
        moduleRepository.delete(module);
    }

}
