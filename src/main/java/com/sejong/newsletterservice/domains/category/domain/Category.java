package com.sejong.newsletterservice.domains.category.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_id_name",
                columnNames = {"category_id", "name"}
        )
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @Column(name = "category_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // project-service와 같은 id를 가져야 함
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
