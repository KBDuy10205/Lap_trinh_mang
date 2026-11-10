-- Game da luan luu 2 nguoi. Target: MySQL 8.0.16+ / 8.4, InnoDB.
-- Chay tren database moi. Khong co DROP TABLE, khong xoa du lieu hien co.
CREATE DATABASE IF NOT EXISTS penalty_game
  CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE penalty_game;
-- Server/connection dung UTC; DATETIME khong tu chuyen mui gio.
SET time_zone = '+00:00';

CREATE TABLE accounts (
  user_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) CHARACTER SET ascii COLLATE ascii_general_ci NOT NULL,
  password_hash VARCHAR(255) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  status ENUM('ACTIVE','LOCKED') NOT NULL DEFAULT 'ACTIVE',
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  UNIQUE KEY uq_accounts_username (username)
) ENGINE=InnoDB;

CREATE TABLE player_profiles (
  user_id BIGINT UNSIGNED PRIMARY KEY,
  display_name VARCHAR(60) NOT NULL,
  avatar_url VARCHAR(500) NULL,
  CONSTRAINT fk_profile_user FOREIGN KEY (user_id) REFERENCES accounts(user_id)
) ENGINE=InnoDB;

CREATE TABLE rooms (
  room_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  room_code CHAR(8) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  owner_id BIGINT UNSIGNED NOT NULL,
  status ENUM('WAITING','PLAYING','CLOSED') NOT NULL DEFAULT 'WAITING',
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  UNIQUE KEY uq_rooms_code (room_code),
  KEY ix_rooms_owner (owner_id),
  CONSTRAINT fk_room_owner FOREIGN KEY (owner_id) REFERENCES accounts(user_id)
) ENGINE=InnoDB;

-- Chi luu thanh vien HIEN TAI. Roi phong thi xoa dong o bang nay.
CREATE TABLE room_members (
  room_id BIGINT UNSIGNED NOT NULL,
  slot TINYINT UNSIGNED NOT NULL,
  user_id BIGINT UNSIGNED NOT NULL,
  is_ready BOOLEAN NOT NULL DEFAULT FALSE,
  joined_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (room_id, slot),
  UNIQUE KEY uq_room_members_user (user_id),
  CONSTRAINT ck_member_slot CHECK (slot IN (1,2)),
  CONSTRAINT ck_member_ready CHECK (is_ready IN (0,1)),
  CONSTRAINT fk_member_room FOREIGN KEY (room_id) REFERENCES rooms(room_id),
  CONSTRAINT fk_member_user FOREIGN KEY (user_id) REFERENCES accounts(user_id)
) ENGINE=InnoDB;

CREATE TABLE matches (
  match_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  room_id BIGINT UNSIGNED NOT NULL,
  status ENUM('PLAYING','FINISHED','CANCELLED') NOT NULL DEFAULT 'PLAYING',
  first_shooter_slot TINYINT UNSIGNED NOT NULL,
  turn_timeout_seconds SMALLINT UNSIGNED NOT NULL DEFAULT 15,
  started_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  KEY ix_matches_room (room_id, match_id),
  CONSTRAINT ck_match_first_slot CHECK (first_shooter_slot IN (1,2)),
  CONSTRAINT ck_match_timeout CHECK (turn_timeout_seconds BETWEEN 1 AND 300),
  CONSTRAINT fk_match_room FOREIGN KEY (room_id) REFERENCES rooms(room_id)
) ENGINE=InnoDB;

-- Ban chup nguoi tham gia; khong phu thuoc thanh vien phong sau nay.
CREATE TABLE match_players (
  match_id BIGINT UNSIGNED NOT NULL,
  slot TINYINT UNSIGNED NOT NULL,
  user_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (match_id, slot),
  UNIQUE KEY uq_match_player (match_id, user_id),
  KEY ix_player_history (user_id, match_id),
  CONSTRAINT ck_player_slot CHECK (slot IN (1,2)),
  CONSTRAINT fk_player_match FOREIGN KEY (match_id) REFERENCES matches(match_id),
  CONSTRAINT fk_player_user FOREIGN KEY (user_id) REFERENCES accounts(user_id)
) ENGINE=InnoDB;

-- Mot dong = MOT cu sut, khong phai mot cap sut.
CREATE TABLE turns (
  match_id BIGINT UNSIGNED NOT NULL,
  turn_no INT UNSIGNED NOT NULL,
  shooter_slot TINYINT UNSIGNED NOT NULL,
  deadline_at DATETIME(3) NOT NULL,
  outcome ENUM('GOAL','SAVED','MISS','VOID') NULL,
  resolution_reason ENUM('BOTH_CHOSE','SHOOTER_TIMEOUT','KEEPER_TIMEOUT','BOTH_TIMEOUT','MATCH_ENDED') NULL,
  resolved_at DATETIME(3) NULL,
  PRIMARY KEY (match_id, turn_no),
  KEY ix_turn_shooter (match_id, shooter_slot),
  CONSTRAINT ck_turn_number CHECK (turn_no >= 1),
  CONSTRAINT ck_turn_resolution CHECK (
    (outcome IS NULL AND resolution_reason IS NULL AND resolved_at IS NULL)
    OR (outcome IS NOT NULL AND resolution_reason IS NOT NULL AND resolved_at IS NOT NULL)
  ),
  CONSTRAINT fk_turn_shooter FOREIGN KEY (match_id, shooter_slot)
    REFERENCES match_players(match_id, slot)
) ENGINE=InnoDB;

-- Du lieu bi mat: chi server doc, khong tra truc tiep entity nay cho client.
-- Khong UPDATE lua chon da chap nhan. Khong co dong = chua chon.
CREATE TABLE turn_choices (
  match_id BIGINT UNSIGNED NOT NULL,
  turn_no INT UNSIGNED NOT NULL,
  player_slot TINYINT UNSIGNED NOT NULL,
  direction ENUM('LEFT','CENTER','RIGHT') NOT NULL,
  chosen_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (match_id, turn_no, player_slot),
  KEY ix_choice_player (match_id, player_slot),
  CONSTRAINT fk_choice_turn FOREIGN KEY (match_id, turn_no)
    REFERENCES turns(match_id, turn_no),
  CONSTRAINT fk_choice_player FOREIGN KEY (match_id, player_slot)
    REFERENCES match_players(match_id, slot)
) ENGINE=InnoDB;

CREATE TABLE match_results (
  match_id BIGINT UNSIGNED PRIMARY KEY,
  winner_slot TINYINT UNSIGNED NULL,
  end_reason ENUM('NORMAL','FORFEIT','CANCELLED') NOT NULL,
  ended_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  KEY ix_result_winner (match_id, winner_slot),
  CONSTRAINT ck_result_winner CHECK (
    (end_reason = 'CANCELLED' AND winner_slot IS NULL)
    OR (end_reason IN ('NORMAL','FORFEIT') AND winner_slot IS NOT NULL)
  ),
  CONSTRAINT fk_result_match FOREIGN KEY (match_id) REFERENCES matches(match_id),
  CONSTRAINT fk_result_player FOREIGN KEY (match_id, winner_slot)
    REFERENCES match_players(match_id, slot)
) ENGINE=InnoDB;

-- ================= TRUY VAN THAM KHAO =================
-- Ung dung dung prepared statements voi placeholder ?. Cac @variable ben duoi
-- chi de thu trong Workbench, khong phai dau vao da duoc xac thuc.
-- SET @user_id = 1; SET @match_id = 1; SET @room_code = 'AB12CD34';

-- 1. Dang nhap; ung dung BCrypt verify password_hash.
-- SELECT user_id, password_hash, status FROM accounts WHERE username = ?;

-- 2. Tim phong bang ma (normalize ma sang chu IN HOA truoc khi truy van).
-- SELECT room_id, owner_id, status FROM rooms WHERE room_code = ?;

-- 3. Thanh vien phong.
-- SELECT rm.slot, rm.user_id, pp.display_name, rm.is_ready
-- FROM room_members rm JOIN player_profiles pp ON pp.user_id = rm.user_id
-- WHERE rm.room_id = ? ORDER BY rm.slot;

-- 4. Ti so: chi dem GOAL da duoc server giai quyet.
-- SELECT mp.slot, mp.user_id,
--        COALESCE(SUM(t.outcome = 'GOAL'), 0) AS goals
-- FROM match_players mp
-- LEFT JOIN turns t ON t.match_id = mp.match_id AND t.shooter_slot = mp.slot
-- WHERE mp.match_id = ? GROUP BY mp.slot, mp.user_id ORDER BY mp.slot;

-- 5. Lich su: phan trang theo match_id, khong OFFSET lon.
-- Trang dau bo dieu kien mp.match_id < ?. Trang sau truyen last_match_id.
-- SELECT mp.match_id, m.started_at, r.ended_at, r.end_reason,
--        CASE WHEN r.winner_slot IS NULL THEN 'CANCELLED'
--             WHEN r.winner_slot = mp.slot THEN 'WIN' ELSE 'LOSS' END AS result,
--        opponent.user_id AS opponent_id, pp.display_name AS opponent_name
-- FROM match_players mp
-- JOIN matches m ON m.match_id = mp.match_id
-- JOIN match_results r ON r.match_id = mp.match_id
-- JOIN match_players opponent ON opponent.match_id = mp.match_id AND opponent.slot <> mp.slot
-- JOIN player_profiles pp ON pp.user_id = opponent.user_id
-- WHERE mp.user_id = ? AND mp.match_id < ?
-- ORDER BY mp.match_id DESC LIMIT 20;

-- 6. Thong ke ca nhan: ket qua FORFEIT van tinh thang/thua; CANCELLED khong tinh.
-- SELECT COUNT(*) AS total_matches,
--        COALESCE(SUM(r.winner_slot = mp.slot), 0) AS wins,
--        COALESCE(SUM(r.winner_slot <> mp.slot), 0) AS losses
-- FROM match_players mp JOIN match_results r ON r.match_id = mp.match_id
-- WHERE mp.user_id = ? AND r.end_reason <> 'CANCELLED';

-- 7. Xem lai luot sut: chi lay lua chon khi luot da ket thuc.
-- SELECT t.turn_no, t.shooter_slot, t.outcome, t.resolution_reason,
--        c.player_slot, c.direction
-- FROM turns t LEFT JOIN turn_choices c
--   ON c.match_id = t.match_id AND c.turn_no = t.turn_no
-- WHERE t.match_id = ? AND t.resolved_at IS NOT NULL
-- ORDER BY t.turn_no, c.player_slot;
