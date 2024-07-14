package com.luciano.ead.course.service;

import com.luciano.ead.course.model.Lesson;
import com.luciano.ead.course.model.ModuleModel;
import com.luciano.ead.course.repository.LessonRepository;
import com.luciano.ead.course.repository.ModuleRepository;
import com.luciano.ead.course.service.impl.ModuleServiceImpl;
import com.luciano.ead.course.util.ModuleModelTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModuleServiceImplTest {
    @InjectMocks
    private ModuleServiceImpl moduleServiceImpl;
    @Mock
    private ModuleRepository moduleRepository;
    @Mock
    private LessonRepository lessonRepository;
    @Test
    void deleteModule() {
        UUID moduleModelId = UUID.randomUUID();
        ModuleModel moduleModel = ModuleModelTestData.moduleModel1();
        moduleModel.setModuleId(moduleModelId);

        List<Lesson> lessonList = Arrays.asList(new Lesson(), new Lesson());

        when(lessonRepository.findAllLessonsIntoModule(moduleModelId)).thenReturn(lessonList);
        moduleServiceImpl.delete(moduleModel);

        assertFalse(lessonList.isEmpty());

        verify(lessonRepository, times(1)).findAllLessonsIntoModule(moduleModelId);
        verify(lessonRepository, times(1)).deleteAll(lessonList);
        verify(moduleRepository, times(1)).delete(moduleModel);

    }
    @Test
    void saveModules() {
        ModuleModel moduleModel = ModuleModelTestData.moduleModel1();
        when(moduleRepository.save(moduleModel)).thenReturn(moduleModel);

        ModuleModel savedModule = moduleServiceImpl.save(moduleModel);
        assertEquals(savedModule, moduleModel);

        verify(moduleRepository, times(1)).save(savedModule);
    }
    @Test
    void FindAllModulesIntoCourse() {
        UUID courseId = UUID.randomUUID();
        List<ModuleModel> expectedModule = Arrays.asList(
                ModuleModelTestData.moduleModel1(),
                ModuleModelTestData.moduleModel1()
        );
        when(moduleRepository.findAllModulesIntoCourse(courseId)).thenReturn(expectedModule);

        List<ModuleModel> actualModel = moduleServiceImpl.findAllModulesIntoCourse(courseId);

        assertEquals(expectedModule, actualModel);

        verify(moduleRepository, times(1)).findAllModulesIntoCourse(courseId);
    }
    @Test
    void findModuleIntoCourse() {
        UUID courseId = UUID.randomUUID();
        UUID moduleId = UUID.randomUUID();

        ModuleModel moduleModel = ModuleModelTestData.moduleModel1();

        when(moduleRepository.findModuleIntoCourse(courseId, moduleId)).thenReturn(Optional.of(moduleModel));

        Optional<ModuleModel> actualModule = moduleServiceImpl.findModuleIntoCourse(courseId, moduleId);

        assertTrue(actualModule.isPresent());
        assertEquals(moduleModel, actualModule.get());

        verify(moduleRepository, times(1)).findModuleIntoCourse(courseId, moduleId);

    }
    @Test
    void findById() {
        UUID moduleId = UUID.randomUUID();
        ModuleModel moduleModel = ModuleModelTestData.moduleModel1();

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(moduleModel));

        Optional<ModuleModel> actualModule = moduleServiceImpl.findById(moduleId);

        assertTrue(actualModule.isPresent());
        assertEquals(moduleModel.getModuleId(), actualModule.get().getModuleId());
        assertEquals(moduleModel.getTitle(), actualModule.get().getTitle());
        assertEquals(moduleModel.getDescription(), actualModule.get().getDescription());
        assertEquals(moduleModel.getCreationDate(), actualModule.get().getCreationDate());
        assertEquals(moduleModel, actualModule.get());

        verify(moduleRepository, times(1)).findById(moduleId);
    }
    @Test
    void testFindAllModulesIntoCourseWithSpec() {
        Specification<ModuleModel> spec = (root, query, criteriaBuilder) -> null;
        Pageable pageable = PageRequest.of(0, 2);

        List<ModuleModel> expectedModules = Arrays.asList(
                ModuleModelTestData.moduleModel1(),
                ModuleModelTestData.moduleModel2()
        );

        Page<ModuleModel> expectedPage = new PageImpl<>(expectedModules, pageable, expectedModules.size());

        when(moduleRepository.findAll(spec, pageable)).thenReturn(expectedPage);

        Page<ModuleModel> actualPage = moduleServiceImpl.findAllModulesIntoCourse(spec, pageable);
        assertEquals(expectedPage, actualPage);
        assertFalse(actualPage.isEmpty());
        assertEquals(expectedModules.size(), actualPage.getSize());

        verify(moduleRepository, times(1)).findAll(spec, pageable);
    }

}
