package com.luciano.ead.course.client;

import com.luciano.ead.course.controller.dto.ResponsePageDTO;
import com.luciano.ead.course.controller.dto.UserDTO;
import com.luciano.ead.course.service.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.authuser}")
    String REQUEST_URL_AUTHUSER;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDTO> searchResult = null;
        String url = REQUEST_URL_AUTHUSER + utilsService.createUrlgetAllUsersByCourse(courseId, pageable);

        log.debug("REQUEST URL: {} ", url);
        log.info("REQUEST URL: {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType =
                    new ParameterizedTypeReference<ResponsePageDTO<UserDTO>>() {
                    };
            ResponseEntity<ResponsePageDTO<UserDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error Request /courses {}", e);
        }
        log.info("Ending Request / users courseId {} ", courseId);
        return new PageImpl<>(searchResult);
    }
}
