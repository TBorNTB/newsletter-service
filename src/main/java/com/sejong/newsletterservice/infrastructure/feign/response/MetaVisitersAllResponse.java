package com.sejong.newsletterservice.infrastructure.feign.response;

import com.sejong.newsletterservice.core.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaVisitersAllResponse {
    private List<PopularPost> popularPosts;
    
    // 각 PostType별 인기글을 가져오는 헬퍼 메서드들
    public PopularPost getNewsPost() {
        return findByType(PostType.NEWS);
    }
    
    public PopularPost getProjectPost() {
        return findByType(PostType.PROJECT);
    }
    
    public PopularPost getArchivePost() {
        return findByType(PostType.ARCHIVE);
    }
    
    private PopularPost findByType(PostType type) {
        if (popularPosts == null) return null;
        return popularPosts.stream()
                .filter(p -> p.getPostType() == type)
                .findFirst()
                .orElse(null);
    }
    
    // 각 PostType별 ID와 좋아요 수를 가져오는 메서드들
    public String getNewsId() {
        PopularPost newsPost = getNewsPost();
        return newsPost != null ? newsPost.getPostId() : "";
    }
    
    public Long getNewsLikeCount() {
        PopularPost newsPost = getNewsPost();
        return newsPost != null ? newsPost.getLikeCount() : 0L;
    }
    
    public String getProjectId() {
        PopularPost projectPost = getProjectPost();
        return projectPost != null ? projectPost.getPostId() : "";
    }
    
    public Long getProjectLikeCount() {
        PopularPost projectPost = getProjectPost();
        return projectPost != null ? projectPost.getLikeCount() : 0L;
    }
    
    public String getArchiveId() {
        PopularPost archivePost = getArchivePost();
        return archivePost != null ? archivePost.getPostId() : "";
    }
    
    public Long getArchiveLikeCount() {
        PopularPost archivePost = getArchivePost();
        return archivePost != null ? archivePost.getLikeCount() : 0L;
    }
}
