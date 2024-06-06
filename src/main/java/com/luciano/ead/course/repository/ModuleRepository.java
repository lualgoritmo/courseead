package com.luciano.ead.course.repository;

import com.luciano.ead.course.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {
    @Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    //    @EntityGraph(attributePaths = {"course"})
//    Module findByTitle(String title);
}
