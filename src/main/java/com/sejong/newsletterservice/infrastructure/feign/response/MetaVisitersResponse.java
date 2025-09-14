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
public class MetaVisitersResponse {
    private Long postId;
    private PostType postType;
}
