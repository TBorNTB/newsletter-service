package com.sejong.newsletterservice.domains.category.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "icon_url", length = 512)
    private String iconUrl;

    public static Category of(String name, String description, String content, String iconUrl) {
        return Category.builder()
                .name(name)
                .description(description)
                .content(content)
                .iconUrl(iconUrl)
                .build();
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateIconUrl(String iconKey) {
        this.iconUrl = iconUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
