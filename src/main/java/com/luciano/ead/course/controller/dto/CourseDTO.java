package com.luciano.ead.course.controller.dto;

import com.luciano.ead.course.enuns.CourseLevel;
import com.luciano.ead.course.enuns.CourseStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageUrl;
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private CourseStatus courseStatus;
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private CourseLevel courseLevel;
    @NotNull
    private UUID userInstructor;


}
