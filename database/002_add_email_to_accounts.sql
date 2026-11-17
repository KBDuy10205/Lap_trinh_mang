-- Migration cho database da tung chay database/penalty_game_mysql.sql truoc 17/11/2026.
-- Khong chay lai file nay neu cot email da ton tai. Khong co DROP hay TRUNCATE.
USE penalty_game;

-- Buoc 1: them cot cho cac tai khoan cu. Tai khoan cu tam thoi co email NULL.
ALTER TABLE accounts
  ADD COLUMN email VARCHAR(254) CHARACTER SET ascii COLLATE ascii_general_ci NULL AFTER username;

-- Buoc 2: nguoi quan tri cap nhat email hop le, duy nhat cho MOI dong email IS NULL.
-- Khong tu sinh email vi se tao du lieu sai. Vi du thuc hien bang PreparedStatement:
-- UPDATE accounts SET email = ? WHERE user_id = ? AND email IS NULL;

-- Buoc 3: chi chay sau khi xac nhan khong con email NULL va khong trung.
-- ALTER TABLE accounts
--   MODIFY COLUMN email VARCHAR(254) CHARACTER SET ascii COLLATE ascii_general_ci NOT NULL,
--   ADD UNIQUE KEY uq_accounts_email (email);
