package com.sejong.newsletterservice.api.controller;

import com.sejong.newsletterservice.api.controller.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.api.controller.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.service.SubscriberService;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/newsletter")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping("/subscribe")
    public ResponseEntity<SubscriberResponse> subscribe(
            @Valid @RequestBody SubscriberRequest subscriberRequest
    ) {
        SubscriberRequestVO subscriberRequestVO = subscriberRequest.toVO();
        SubscriberResponse response = subscriberService.register(subscriberRequestVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
