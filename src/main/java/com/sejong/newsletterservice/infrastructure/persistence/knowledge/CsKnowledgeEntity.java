package com.sejong.newsletterservice.infrastructure.persistence.knowledge;

import com.sejong.newsletterservice.domain.model.CsKnowledge;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "cs_knowledge",
        indexes = {
                @Index(name = "idx_cs_knowledge_category_id", columnList = "categoryName, id")
        }
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CsKnowledgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryName", columnDefinition = "VARCHAR(50)", nullable = false)
    private MailCategoryName mailCategoryName;

    private LocalDateTime createdAt;

    public CsKnowledge toDomain() {
        return CsKnowledge.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(mailCategoryName)
                .createdAt(createdAt)
                .build();
    }

    public static CsKnowledgeEntity from(CsKnowledge knowledge) {
        return CsKnowledgeEntity.builder()
                .title(knowledge.getTitle())
                .content(knowledge.getContent())
                .mailCategoryName(knowledge.getCategory())
                .createdAt(knowledge.getCreatedAt())
                .build();
    }
}
