package com.sejong.newsletterservice.support.feign.response;

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
    private String contentType;
    private String title;
    private String content;
    private String category;
    private String createdAt;
    private long likeCount;
    private long viewCount;
}
