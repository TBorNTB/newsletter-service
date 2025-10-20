# Newsletter Service - PlantUML 문서

이 문서는 Newsletter Service의 PlantUML 클래스 다이어그램을 포함합니다.

## PlantUML 설정

### IntelliJ IDEA에서 PlantUML 사용하기

1. **PlantUML 플러그인 설치**
   - `File` → `Settings` → `Plugins`
   - "PlantUML" 검색 후 설치
   - IDE 재시작

2. **Graphviz 설치 (Windows)**
   ```bash
   # Chocolatey 사용
   choco install graphviz
   
   # 또는 직접 다운로드
   # https://graphviz.org/download/
   ```

3. **PlantUML 설정**
   - `File` → `Settings` → `Tools` → `PlantUML`
   - Graphviz dot executable path 설정: `C:\Program Files\Graphviz\bin\dot.exe`

### VS Code에서 PlantUML 사용하기

1. **PlantUML 확장 설치**
   - `PlantUML` 확장 설치
   - `Markdown Preview Enhanced` 확장 설치 (선택사항)

2. **Graphviz 설치**
   - 위와 동일한 방법으로 Graphviz 설치

## 클래스 다이어그램

### 1. 전체 아키텍처 다이어그램

```plantuml
@startuml NewsletterServiceArchitecture
!theme plain
skinparam classAttributeIconSize 0
skinparam packageStyle rectangle
skinparam backgroundColor white
skinparam classFontSize 10
skinparam packageFontSize 12
skinparam packageFontStyle bold
skinparam linetype ortho
direction TB

package "Application Layer" {
  class NewsletterService
  class NewsletterScheduler
  class SubscriberService
  class VerificationService
}

package "Core Layer" {
  class Subscriber
  class MailCategory
  class SentLog
  class CsKnowledge
}

package "Infrastructure Layer" {
  class JpaSubscriberRepository
  class JpaMailCategoryRepository
  class JpaSentLogRepository
  class GmailService
  class RedisCacheService
}

package "External Services" {
  class ArchiveClient
  class MetaClient
  class ProjectClient
  class UserClient
}

' Application Layer relationships
NewsletterService --> NewsletterScheduler
NewsletterService --> SubscriberService
SubscriberService --> VerificationService

' Core Layer relationships
SubscriberService --> Subscriber
SubscriberService --> MailCategory
NewsletterService --> SentLog
NewsletterService --> CsKnowledge

' Infrastructure Layer relationships
SubscriberService --> JpaSubscriberRepository
SubscriberService --> JpaMailCategoryRepository
NewsletterService --> JpaSentLogRepository
NewsletterService --> GmailService
NewsletterService --> RedisCacheService

' External Services relationships
NewsletterService --> ArchiveClient
NewsletterService --> MetaClient
NewsletterService --> ProjectClient
NewsletterService --> UserClient

@enduml
```

### 2. 구독자 관리 도메인 다이어그램

```plantuml
@startuml SubscriberDomain
!theme plain
skinparam classAttributeIconSize 0
skinparam packageStyle rectangle
skinparam backgroundColor white
skinparam classFontSize 10
skinparam packageFontSize 12
skinparam packageFontStyle bold
skinparam linetype ortho
direction TB

package "Controller" {
  class SubscriberController
}

package "Service" {
  class SubscriberService
  class VerificationService
}

package "DTO" {
  class SubscriberRequest
  class SubscriberResponse
  class VerifyRequest
  class VerificationResponse
}

package "Entity" {
  class SubscriberEntity
  class Subscriber
}

package "Repository" {
  interface SubscriberRepository
  class JpaSubscriberRepository
  class SpringDataJpaSubscriberRepository
}

package "Cache" {
  class SubscriberCacheService
}

' Relationships
SubscriberController --> SubscriberService
SubscriberController --> VerificationService

SubscriberService --> SubscriberRequest
SubscriberService --> SubscriberResponse
SubscriberService --> SubscriberEntity
SubscriberService --> SubscriberRepository
SubscriberService --> SubscriberCacheService

VerificationService --> VerifyRequest
VerificationService --> VerificationResponse
VerificationService --> SubscriberEntity

SubscriberEntity --> Subscriber
JpaSubscriberRepository ..|> SubscriberRepository
SpringDataJpaSubscriberRepository ..|> SubscriberRepository

@enduml
```

### 3. 뉴스레터 발송 도메인 다이어그램

```plantuml
@startuml NewsletterDomain
!theme plain
skinparam classAttributeIconSize 0
skinparam packageStyle rectangle
skinparam backgroundColor white
skinparam classFontSize 10
skinparam packageFontSize 12
skinparam packageFontStyle bold
skinparam linetype ortho
direction TB

package "Service" {
  class NewsletterService
  class NewsletterScheduler
  class NewsletterDomainService
  class NewsletterEmailSender
}

package "Email" {
  class GmailService
  class EmailContentBuilder
  class VerificationEmailService
}

package "Entity" {
  class MailCategory
  class SentLog
  class CsKnowledge
}

package "Repository" {
  interface MailCategoryRepository
  class JpaMailCategoryRepository
  class JpaSentLogRepository
  class JpaCsKnowledgeRepository
}

package "Cache" {
  class SentLogCacheService
}

package "External" {
  class ArchiveClient
  class MetaClient
  class ProjectClient
}

' Relationships
NewsletterService --> NewsletterScheduler
NewsletterService --> NewsletterDomainService
NewsletterService --> NewsletterEmailSender

NewsletterDomainService --> MailCategory
NewsletterDomainService --> SentLog
NewsletterDomainService --> CsKnowledge
NewsletterDomainService --> ArchiveClient
NewsletterDomainService --> MetaClient
NewsletterDomainService --> ProjectClient

NewsletterEmailSender --> GmailService
NewsletterEmailSender --> EmailContentBuilder
NewsletterEmailSender --> VerificationEmailService

NewsletterService --> MailCategoryRepository
NewsletterService --> JpaSentLogRepository
NewsletterService --> JpaCsKnowledgeRepository
NewsletterService --> SentLogCacheService

JpaMailCategoryRepository ..|> MailCategoryRepository

@enduml
```

### 4. 데이터베이스 엔티티 관계 다이어그램

```plantuml
@startuml DatabaseEntities
!theme plain
skinparam classAttributeIconSize 0
skinparam entityStyle rectangle
skinparam backgroundColor white
skinparam classFontSize 10
skinparam packageFontSize 12
skinparam packageFontStyle bold
skinparam linetype ortho
direction TB

entity "SubscriberEntity" {
  * id: Long
  --
  * email: String
  * isVerified: Boolean
  * emailFrequency: EmailFrequency
  * createdAt: LocalDateTime
  * updatedAt: LocalDateTime
}

entity "MailCategoryEntity" {
  * id: Long
  --
  * name: String
  * description: String
  * isActive: Boolean
}

entity "SentLogEntity" {
  * id: Long
  --
  * subscriberId: Long
  * mailCategoryId: Long
  * sentAt: LocalDateTime
  * status: String
}

entity "CsKnowledgeEntity" {
  * id: Long
  --
  * title: String
  * content: String
  * category: String
  * createdAt: LocalDateTime
}

' Relationships
SubscriberEntity ||--o{ SentLogEntity : "has many"
MailCategoryEntity ||--o{ SentLogEntity : "has many"

@enduml
```

## 사용 방법

### 1. 다이어그램 생성

각 PlantUML 코드 블록을 `.puml` 파일로 저장하고 PlantUML 플러그인으로 렌더링할 수 있습니다.

### 2. 다이어그램 수정

코드 블록을 수정하여 다이어그램을 업데이트할 수 있습니다. 주요 수정 포인트:

- 클래스 추가/제거
- 관계선 추가/제거
- 패키지 구조 변경
- 속성 및 메서드 추가

### 3. 문서 업데이트

새로운 기능이나 클래스가 추가될 때마다 이 문서를 업데이트하여 최신 상태를 유지하세요.

## 참고 자료

- [PlantUML 공식 문서](https://plantuml.com/)
- [PlantUML 클래스 다이어그램 가이드](https://plantuml.com/class-diagram)
- [Graphviz 다운로드](https://graphviz.org/download/)

