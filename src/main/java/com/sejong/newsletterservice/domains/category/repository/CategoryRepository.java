package com.sejong.newsletterservice.domains.category.repository;

import com.sejong.newsletterservice.domains.category.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findAllByNameIn(List<String> names);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO category (category_id, name, description, content, icon_url)
            VALUES (:id, :name, :description, :content, :iconUrl)
            ON DUPLICATE KEY UPDATE
                name = :name, description = :description, content = :content, icon_url = :iconUrl
            """, nativeQuery = true)
    void upsert(@Param("id") Long id,
                @Param("name") String name,
                @Param("description") String description,
                @Param("content") String content,
                @Param("iconUrl") String iconUrl);
}
