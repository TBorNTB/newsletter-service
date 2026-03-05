CREATE TABLE IF NOT EXISTS subscriber
(
    id                 BIGINT      NOT NULL AUTO_INCREMENT,
    email              VARCHAR(50) NOT NULL,
    email_frequency    VARCHAR(50) NOT NULL,
    created_at         DATETIME(6),
    active             TINYINT(1)  NOT NULL,
    chasing_popularity TINYINT(1)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_subscriber_email UNIQUE (email),
    INDEX idx_subscriber_email_frequency (email_frequency)
);

CREATE TABLE IF NOT EXISTS category
(
    category_id BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(200),
    content     TEXT,
    icon_url    VARCHAR(512),
    PRIMARY KEY (category_id),
    CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS subscriber_category
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    subscriber_id BIGINT NOT NULL,
    category_id   BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_subscriber_category_subscriber FOREIGN KEY (subscriber_id) REFERENCES subscriber (id),
    CONSTRAINT fk_subscriber_category_category FOREIGN KEY (category_id) REFERENCES category (category_id)
);


INSERT INTO category (name, description, content, icon_url) VALUES
('웹_해킹', 'SQL Injection, XSS, CSRF 등 웹 애플리케이션 보안 취약점 분석 및 대응',
'웹 해킹은 웹 애플리케이션에서 발생할 수 있는 다양한 보안 취약점을 실습하고 분석하는 분야입니다.
SQL 인젝션, XSS(Cross-Site Scripting), CSRF(Cross-Site Request Forgery) 등의 공격 기법과 이에 대한 방어 매커니즘을 실습합니다. 자동화된 취약점 스캐너 개발, 웹 애플리케이션 펜테스팅 도구 사용법, 그리고 현실적인 공격 시나리오 분석을 다룹니다.',
 'https://files.sejongssg.kr/project-service/category-icon/1/div_a447bd6f-d7e3-4bfa-92f7-fe83a2535d51.jpg'),

('리버싱', '바이너리 분석, 역공학 기술을 통한 소프트웨어 구조 분석 및 이해',
'리버싱(역공학)은 컴파일된 바이너리 파일을 분석하여 원본 코드의 로직과 구조를 이해하는 기술입니다.
IDA Pro, Ghidra, x64dbg와 같은 전문 도구를 사용하여 바이너리를 분석하고, 어셈블리어를 해독하며, 프로그램의 실행 흐름을 파악합니다. 또한 패킹된 악성코드 언패킹, 프로텍션 우회, 크랙미 문제 해결 등의 실습을 진행합니다.',
 'https://files.sejongssg.kr/project-service/category-icon/2/리버싱_3b630b8a-375e-4570-9150-c4681285460d.jpg'),

('시스템_해킹', 'Buffer Overflow, ROP 등 시스템 레벨 취약점 분석 및 익스플로잇 개발',
'시스템 해킹은 운영체제와 시스템 레벨에서 발생하는 취약점을 분석하고 익스플로잇을 개발하는 분야입니다.
Buffer Overflow, Format String Bug, Use-After-Free와 같은 메모리 corruption 취약점을 실습하고, ROP(Return-Oriented Programming), JOP(Jump-Oriented Programming) 등의 고급 익스플로잇 기법을 학습합니다.
또한 ASLR, DEP, Stack Canary와 같은 보안 메커니즘 우회 기법도 다룹니다.', 'https://files.sejongssg.kr/project-service/category-icon/3/시스템해킹_abef4f57-3a6c-4717-a377-d1931077399e.jpg'),

('디지털_포렌식', '디지털 증거 수집 및 분석, 사고 대응을 위한 포렌식 기법',
'디지털 포렌식은 사이버 범죄나 보안 사고 발생 시 디지털 증거를 수집하고 분석하는 전문 분야입니다. 파일 시스템 분석, 메모리 덤프 분석, 네트워크 패킷 분석을 통해 침해 흔적을 추적하고 사고 원인을 규명합니다.
Volatility, Autopsy, Wireshark 등의 전문 도구를 활용하여 실제 사고 시나리오를 분석하고, 법정에서 인정받을 수 있는 증거 수집 절차를 학습합니다.', 'https://files.sejongssg.kr/project-service/category-icon/4/디지털포렌식_c0515ff7-061c-4980-ba03-654d82da8623.jpg'),

('네트워크_보안', '네트워크 트래픽 분석, 침입 탐지 및 방화벽 보안 기술',
'네트워크 보안은 네트워크를 통한 공격과 방어에 대한 전반적인 보안 기술을 다루는 분야입니다.
패킷 분석을 통한 네트워크 트래픽 모니터링, IDS/IPS 시스템 구축 및 운영, 방화벽 정책 설계, 그리고 무선 네트워크 보안을 실습합니다.
또한 네트워크 스캐닝, 포트 스캐닝, 네트워크 기반 공격 기법들과 이에 대한 탐지 및 차단 방법을 학습합니다.', 'https://files.sejongssg.kr/project-service/category-icon/5/네트워크_보안_4324f18c-2bff-4bb6-b95a-8e3cd14038b6.png'),

('IoT_보안', '스마트 기기의 보안 취약점을 분석 및 대응',
'IoT 보안은 점점 증가하는 사물인터넷 기기들의 보안 취약점을 분석하고 대응하는 새로운 보안 분야입니다.
임베디드 시스템 보안, 펌웨어 분석, 하드웨어 해킹, 무선 통신 프로토콜 보안을 다룹니다.
실제 IoT 기기를 대상으로 펌웨어 추출, 바이너리 분석, 통신 패킷 분석을 실습하고, UART, JTAG와 같은 하드웨어 인터페이스를 활용한 분석 기법을 학습합니다.', 'https://files.sejongssg.kr/project-service/category-icon/6/Iot_보안_8c5658e2-9003-479c-9b03-685c089eaa13.jpg'),

('암호학', '현대 암호학 이론, 암호 시스템 분석 및 보안 프로토콜 구현',
'암호학은 정보 보안의 핵심 이론과 기술을 다루는 근본적인 보안 분야입니다.
대칭키/비대칭키 암호, 해시 함수, 디지털 서명의 이론적 배경과 실제 구현을 학습합니다.
RSA, AES, ECC와 같은 주요 암호 알고리즘의 동작 원리와 보안성을 분석하고, TLS/SSL, PKI와 같은 실제 보안 프로토콜의 설계와 취약점을 연구합니다.', 'https://files.sejongssg.kr/project-service/category-icon/7/암호학_6f545c48-f811-43a5-89f9-fab06b99766c.jpg');