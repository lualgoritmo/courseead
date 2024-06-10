package com.luciano.ead.course.controller.dto;

import com.luciano.ead.course.model.Lesson;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class LessonDTO {

    //private UUID lessonId;
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String videoUrl;

    public Lesson toEntity() {
        Lesson lesson = new Lesson();
        //lesson.setLessonId(this.lessonId);
        lesson.setTitle(this.title);
        lesson.setDescription(this.description);
        lesson.setVideoUrl(this.videoUrl);

        return lesson;
    }

    public static LessonDTO fromEntity(Lesson lesson) {
        LessonDTO lessonDTO = new LessonDTO();
        //lessonDTO.setLessonId(lesson.getLessonId());
        lessonDTO.setTitle(lesson.getTitle());
        lessonDTO.setDescription(lesson.getDescription());
        lessonDTO.setVideoUrl(lesson.getVideoUrl());

        return lessonDTO;
    }
}
