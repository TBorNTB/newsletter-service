--  cs_knowledge
INSERT IGNORE INTO cs_knowledge (title, category_name, content, created_at)
VALUES
  ('암호화의 기본22', 'CRYPTOGRAPHY', '암호화는 정보를 인코딩하여 허가되지 않은 접근으로부터 보호하는 기술입니다.', NOW()),
  ('RSA 알고리즘 개요', 'CRYPTOGRAPHY', 'RSA는 공개키 기반 암호화 방식으로, 큰 소수의 곱셈 문제를 이용합니다.', NOW()),
  ('디지털 포렌식 개론', 'DIGITAL_FORENSICS', '디지털 포렌식은 디지털 장비에서 증거를 수집, 분석하는 기술입니다.', NOW()),
  ('로그 분석 기초', 'DIGITAL_FORENSICS', '로그 분석은 사건의 타임라인을 재구성하는 데 중요한 역할을 합니다.', NOW()),
  ('IoT 보안 이슈', 'IOT_SECURITY', 'IoT 기기는 보안 업데이트가 어려워 공격에 취약할 수 있습니다.', NOW()),
  ('디바이스 인증 방식', 'IOT_SECURITY', 'IoT 환경에서는 디바이스의 식별과 인증이 핵심입니다.', NOW()),
  ('패킷 스니핑이란?', 'NETWORK_SECURITY', '패킷 스니핑은 네트워크 상의 데이터를 도청하는 공격입니다.', NOW()),
  ('방화벽의 역할', 'NETWORK_SECURITY', '방화벽은 외부 네트워크로부터 내부 네트워크를 보호하는 시스템입니다.', NOW()),
  ('리버싱이란 무엇인가', 'REVERSING', '리버싱은 프로그램의 동작을 분석하는 과정입니다.', NOW()),
  ('디버깅과 역공학', 'REVERSING', '디버깅은 프로그램의 흐름을 추적하여 리버싱에 도움을 줍니다.', NOW()),
  ('권한 상승 공격', 'SYSTEM_HACKING', '권한 상승은 제한된 사용자 계정이 관리자 권한을 획득하는 공격입니다.', NOW()),
  ('쉘코드란?', 'SYSTEM_HACKING', '쉘코드는 시스템 제어를 위해 작성된 머신 코드입니다.', NOW()),
  ('XSS 공격이란?', 'WEB_HACKING', 'XSS는 악의적인 스크립트를 삽입하여 클라이언트를 공격하는 기법입니다.', NOW()),
  ('SQL 인젝션 개요', 'WEB_HACKING', 'SQL 인젝션은 DB 쿼리를 조작하여 인증 우회를 시도합니다.', NOW());

--  subscriber
INSERT IGNORE INTO subscriber (id, created_at, email, email_frequency)
VALUES
  (1, '2025-06-21 07:33:02.597425', 'kkd06144@naver.com', 'DAILY'),
  (4, '2025-06-22 16:15:20.285494', 'kkd06155@naver.com', 'DAILY'),
  (5, '2025-06-22 19:53:13.639288', 'drsoombre@naver.com', 'DAILY'),
  (6, '2025-06-22 19:54:13.257348', 'sisubada@sju.ac.kr', 'DAILY'),
  (7, '2025-06-22 19:54:55.982282', 'haeun4345@naver.com', 'DAILY');

--  mailcategory
INSERT IGNORE INTO mailcategory (subscriber_id, category_name)
VALUES
  (1, 'CRYPTOGRAPHY'),
  (4, 'CRYPTOGRAPHY'),
  (5, 'CRYPTOGRAPHY'),
  (6, 'CRYPTOGRAPHY'),
  (7, 'CRYPTOGRAPHY');
