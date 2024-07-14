package com.luciano.ead.course.controller;

import com.luciano.ead.course.client.AuthUserClient;
import com.luciano.ead.course.controller.dto.SubscriptionDTO;
import com.luciano.ead.course.controller.dto.UserDTO;
import com.luciano.ead.course.enuns.UserStatus;
import com.luciano.ead.course.model.Course;
import com.luciano.ead.course.model.CourseUser;
import com.luciano.ead.course.service.CourseService;
import com.luciano.ead.course.service.CourseUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class CourseUserController {
    @Autowired
    AuthUserClient authUserClient;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseUserService courseUserService;

    @GetMapping("/users/{courseId}users")
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "userId",
                    direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "courseId") UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/users/{courseId}users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionDTO subscriptionDTO) {
        ResponseEntity<UserDTO> responseUser;

        Optional<Course> optionalCourse = courseService.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }
        if (courseUserService.existsByCourseAndUserId(optionalCourse.get(), subscriptionDTO.getUserId())) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("Erros: subscription already exists!");
        }
        try {
            responseUser = authUserClient.getOneUserById(subscriptionDTO.getUserId());
            if (responseUser.getBody().getUserStatus().equals(UserStatus.BLOKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User Not Found");
            }
        }

        CourseUser courseUser = courseUserService.save(optionalCourse.get().convertToCourseUser(subscriptionDTO.getUserId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(courseUser);
    }

}
