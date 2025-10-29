package com.sejong.newsletterservice.infrastructure.feign.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentResponse {
    private String id;
    private String contentType; // PROJECT, NEWS, CS-KNOWLEDGE

    private String title;
    private String content;
    private String category; // 카테고리 정보

    private String createdAt;
    private long likeCount;
    private long viewCount;
}