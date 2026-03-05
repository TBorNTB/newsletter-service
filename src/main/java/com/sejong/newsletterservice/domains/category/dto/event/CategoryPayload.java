package com.sejong.newsletterservice.domains.category.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryPayload {

    private Long id;
    private String name;

    private String description;
    private String content;
    private String iconUrl;

}

