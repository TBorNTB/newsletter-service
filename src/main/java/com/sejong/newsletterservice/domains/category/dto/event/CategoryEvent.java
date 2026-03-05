package com.sejong.newsletterservice.domains.category.dto.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejong.newsletterservice.support.common.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryEvent {

    private Long aggregateId;
    private Type type;
    private long occurredAt;
    private CategoryPayload categoryPayload;

    public static CategoryEvent fromJson(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(message, CategoryEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 실패: " + e.getMessage(), e);
        }
    }
}
