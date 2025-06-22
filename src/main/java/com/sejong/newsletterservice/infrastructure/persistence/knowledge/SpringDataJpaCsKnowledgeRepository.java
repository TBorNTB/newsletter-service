package com.sejong.newsletterservice.infrastructure.persistence.knowledge;

import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaCsKnowledgeRepository extends JpaRepository<CsKnowledgeEntity, Long> {

    List<CsKnowledgeEntity> findAllByMailCategoryName(MailCategoryName mailCategoryName);

    @Query(value = """
    SELECT * FROM cs_knowledge k
    WHERE k.category_name = :categoryName
      AND k.id NOT IN (
        SELECT s.cs_knowledge_id 
        FROM sent_log s 
        WHERE s.email = :email AND s.cs_knowledge_id IS NOT NULL
      )
    ORDER BY RAND()
    LIMIT 1
""", nativeQuery = true)
    Optional<CsKnowledgeEntity> findRandomUnsent(@Param("categoryName") String categoryName,
                                                 @Param("email") String email);
    //자꾸 Enum CategoryName를 사용했었는데 들어가는 값하고 반환되는 categoryName이 다른 문제가 있었다.
    //이유를 찾아보니 nativeQuery 에서는 Enum을 바인딩을 잘 못할 수도 있다고 한다. 그래서 String으로 받아야 한다고 한다.
    //jpql 쓰면 되지 않나?? 그러나 Rand()함수 사용시 문제가 있어 nativeQuery를 사용했따.
    //오류 찾는데 너무 오래 걸렸다 ㅠ
}
