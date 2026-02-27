# DB Migration

Flyway를 사용한 데이터베이스 스키마 버전 관리 디렉터리입니다.

## 파일 네이밍 규칙

```
V{YYYYMMDD}__{설명}.sql
```

| 구성 요소 | 설명 | 예시 |
|-----------|------|------|
| `V` | Flyway versioned migration 접두사 (필수) | `V` |
| `{YYYYMMDD}` | 작업 날짜 (버전 식별자) | `20260227` |
| `__` | 구분자 (언더바 2개, 필수) | `__` |
| `{설명}` | 변경 내용 요약 (snake_case) | `create_tables` |

> **주의:** 같은 날짜에 여러 파일이 필요한 경우 `V20260227_1__`, `V20260227_2__` 처럼 숫자를 추가합니다.

## 작성 규칙

- 한 번 적용된 스크립트는 **절대 수정하지 않습니다** (Flyway가 체크섬으로 검증)
- 기존 스크립트를 변경해야 할 경우 **새 버전 파일을 추가**합니다
- DDL(CREATE, ALTER, DROP)과 DML(INSERT, UPDATE, DELETE)은 파일을 분리합니다
- 멱등성을 위해 `CREATE TABLE IF NOT EXISTS`, `INSERT IGNORE` 등을 활용합니다

## 파일 목록

| 파일명 | 내용 |
|--------|------|
| `V20260227__init.sql` | subscriber, mailcategory 테이블 초기 생성 |

## 새 마이그레이션 추가 방법

1. `V{오늘날짜}__{설명}.sql` 파일을 이 디렉터리에 생성합니다
2. SQL을 작성합니다
3. 애플리케이션 실행 시 Flyway가 자동으로 적용합니다

## 관련 설정

`application.yml`의 Flyway 설정:

```yaml
spring:
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
```