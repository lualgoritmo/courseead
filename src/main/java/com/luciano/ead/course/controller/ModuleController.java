package com.luciano.ead.course.controller;

import com.luciano.ead.course.controller.dto.ModuleDTO;
import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.ModuleModel;
import com.luciano.ead.course.service.CourseService;
import com.luciano.ead.course.service.LessonService;
import com.luciano.ead.course.service.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LessonService lessonService;

    @PostMapping("courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(
            @PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid ModuleDTO moduleDTO) {

        Optional<Course> courseIdOption = courseService.findById(courseId);
        if (!courseIdOption.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ourse Not FoundC");
        }

        var moduleModel = new ModuleModel();

        BeanUtils.copyProperties(moduleDTO, moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setCourse(courseIdOption.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));

    }

    @GetMapping("courses/{courseId}/modules")
    public ResponseEntity<List<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllModulesIntoCourse(courseId));
    }

    @PutMapping("courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(
            @PathVariable(value = "courseId") UUID courseId,
            @PathVariable(value = "moduleId") UUID moduleId
    ) {

        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);

        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this Course");
        }

        return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
    }

    @PutMapping("courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(
            @PathVariable(value = "courseId") UUID courseId,
            @PathVariable(value = "moduleId") UUID moduleId,
            @RequestBody @Valid ModuleDTO moduleDTO) {

        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);

        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this Course");
        }
        var moduleModel = moduleModelOptional.get();

        moduleModel.setTitle(moduleDTO.getTitle());
        moduleModel.setDescription(moduleDTO.getDescription());

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(moduleModel));
    }

    @DeleteMapping("courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(
            @PathVariable(value = "courseId") UUID courseId,
            @PathVariable(value = "moduleId") UUID moduleId) {

        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this Course");
        }

        moduleService.delete(moduleModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Module Deleted with Successfully");
    }

}
