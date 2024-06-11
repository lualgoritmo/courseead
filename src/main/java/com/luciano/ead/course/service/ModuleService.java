package com.luciano.ead.course.service;

import com.luciano.ead.course.model.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
    void delete(ModuleModel module);

    ModuleModel save(ModuleModel moduleModel);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<ModuleModel> findAllModulesIntoCourse(UUID courseId);

    Optional<ModuleModel> findById(UUID moduleId);

    Page<ModuleModel> findAllModulesIntoCourse(Specification<ModuleModel> spec, Pageable pageable);
}
