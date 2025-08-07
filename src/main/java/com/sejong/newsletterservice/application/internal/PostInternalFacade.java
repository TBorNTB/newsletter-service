package com.sejong.newsletterservice.application.internal;

import com.sejong.newsletterservice.core.enums.PostType;
import com.sejong.newsletterservice.core.error.code.ErrorCode;
import com.sejong.newsletterservice.core.error.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostInternalFacade {
    private final ProjectInternalService projectInternalService;
    private final ArchiveInternalService archiveInternalService;

    public Object getFavoritePost(PostType postType) {
        return switch (postType) {
            case ARCHIVE -> archiveInternalService.getFavoritePost();
            case PROJECT -> projectInternalService.getFavoritePost();
            default -> throw new ApiException(ErrorCode.BAD_REQUEST, "해당 PostType은 존재하지 않습니다.");
        };
    }
}
