ALTER TABLE `user_info`
ADD COLUMN `total_finance_peoples`  int(11) NOT NULL DEFAULT 0 COMMENT '账户金额不为零的理财用户数' AFTER `not_follow_total`;