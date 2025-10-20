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
  (4, 'SYSTEM_HACKING'),
  (5, 'CRYPTOGRAPHY'),
  (6, 'CRYPTOGRAPHY'),
  (7, 'CRYPTOGRAPHY');
