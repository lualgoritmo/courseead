package com.luciano.ead.course.controller;

import com.luciano.ead.course.controller.dto.CourseDTO;
import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.service.CourseService;
import com.luciano.ead.course.specification.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseDTO courseDTO) {
        var course = new Course();
        BeanUtils.copyProperties(courseDTO, course);

        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));

    }

    @GetMapping
    public ResponseEntity<Page<Course>> getAllcourses(
            SpecificationTemplate.CourseSpec spec,
            @PageableDefault(page = 0,
                    size = 10,
                    sort = "courseId",
                    direction = Sort.Direction.ASC
            ) Pageable pageable,
            @RequestParam(required = false) UUID userId) {
        if (userId != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(courseService.findAll(SpecificationTemplate
                            .courseUserId(userId).and(spec), pageable));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<Course> optionalCourse = courseService.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalCourse.get());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<Course> courseIdOptional = courseService.findById(courseId);
        if (!courseIdOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }

        courseService.delete(courseIdOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("course deleted sucessfully");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(
            @PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid CourseDTO courseDTO) {
        Optional<Course> courseIdOptional = courseService.findById(courseId);
        if (!courseIdOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }

        var updateCourse = courseIdOptional.get();
        BeanUtils.copyProperties(courseDTO, updateCourse);
        updateCourse.setName(courseDTO.getName());
        updateCourse.setDescription(courseDTO.getDescription());
        updateCourse.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        updateCourse.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(updateCourse));
    }
}
