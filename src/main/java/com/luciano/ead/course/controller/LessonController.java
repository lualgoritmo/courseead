package com.luciano.ead.course.controller;

import com.luciano.ead.course.controller.dto.LessonDTO;
import com.luciano.ead.course.model.Lesson;
import com.luciano.ead.course.model.ModuleModel;
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
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class LessonController {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private LessonService lessonService;

    @PostMapping("modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(
            @PathVariable(value = "moduleId") UUID moduleId,
            @RequestBody @Valid LessonDTO lessonDTO) {

        Optional<ModuleModel> moduleOption = moduleService.findById(moduleId);

        if (!moduleOption.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found");
        }

        Lesson lesson = lessonDTO.toEntity();
        BeanUtils.copyProperties(lessonDTO, lesson);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(moduleOption.get());

        LessonDTO responseDTO = LessonDTO.fromEntity(lessonService.save(lesson));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

//    @PostMapping("modules/{moduleId}/lessons")
//    public ResponseEntity<Object> saveLesson(
//            @PathVariable(value = "moduleId") UUID moduleId,
//            @RequestBody @Valid LessonDTO lessonDTO) {
//
//        Optional<ModuleModel> moduleIdOption = moduleService.findById(moduleId);
//
//        if (!moduleIdOption.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not FoundC");
//        }
//
//        var lesson = new Lesson();
//
//        BeanUtils.copyProperties(lessonDTO, lesson);
//
//        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
//        lesson.setModule(moduleIdOption.get());
//        lesson.setDescription(lessonDTO.getDescription());
//        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
//
//    }
    @GetMapping("modules/{moduleId}/lessons")
    public ResponseEntity<List<LessonDTO>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId) {
        List<Lesson> lessons = lessonService.findByAllLessons(moduleId);

        List<LessonDTO> lessonDTOs = lessons.stream()
                .map(LessonDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(lessonDTOs);
    }
    @GetMapping("modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(
            @PathVariable(value = "moduleId") UUID moduleId,
            @PathVariable(value = "lessonId") UUID lessonId
    ) {

        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

        if (!lessonOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found for this Module");
        }

        return ResponseEntity.status(HttpStatus.OK).body(LessonDTO.fromEntity(lessonOptional.get()));
    }

    @PutMapping("modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(
            @PathVariable(value = "moduleId") UUID courseId,
            @PathVariable(value = "lessonId") UUID moduleId,
            @RequestBody @Valid LessonDTO lessonDTO) {

        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(courseId, moduleId);

        if (!lessonOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this Course");
        }
        var lesson = lessonOptional.get();

        lesson.setTitle(lessonDTO.getTitle());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setVideoUrl(lessonDTO.getVideoUrl());

        return ResponseEntity.status(HttpStatus.OK).body(LessonDTO.fromEntity(lessonService.save(lesson)));
    }

    @DeleteMapping("modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(
            @PathVariable(value = "moduleId") UUID moduleId,
            @PathVariable(value = "lessonId") UUID lessonId) {

        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (!lessonOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this Course");
        }

        lessonService.delete(lessonOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Lesson Deleted with Successfully");
    }
}
