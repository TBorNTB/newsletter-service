--  subscriber
INSERT IGNORE INTO subscriber (created_at, email, email_frequency)
VALUES
  ('2025-01-15 09:00:00', 'drsoombre@naver.com', 'WEEKLY'),
  ('2025-01-16 10:30:00', 'kkd06144@naver.com', 'WEEKLY'),
  ('2025-01-17 14:20:00', 'user3@sju.ac.kr', 'DAILY'),
  ('2025-01-18 16:45:00', 'user4@naver.com', 'WEEKLY'),
  ('2025-01-19 11:10:00', 'user5@gmail.com', 'DAILY'),
  ('2025-01-20 13:25:00', 'user6@sju.ac.kr', 'WEEKLY');

--  mailcategory
INSERT IGNORE INTO mailcategory (subscriber_id, category_name)
VALUES
  (1, 'SYSTEM_HACKING'),
  (1, 'WEB_HACKING'),
  (2, 'CRYPTOGRAPHY'),
  (2, 'NETWORK_SECURITY'),
  (3, 'DIGITAL_FORENSICS'),
  (4, 'REVERSING'),
  (4, 'IOT_SECURITY'),
  (5, 'WEB_HACKING'),
  (5, 'CRYPTOGRAPHY'),
  (5, 'NETWORK_SECURITY'),
  (6, 'SYSTEM_HACKING');
