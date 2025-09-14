package com.sejong.newsletterservice.infrastructure.feign.response;

import com.sejong.newsletterservice.core.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopularPost {
    private String title;
    private PostType postType;
    private String postId;
    private Long likeCount;
}
