SET NAMES utf8mb4; $$$
SET FOREIGN_KEY_CHECKS = 0;$$$
DROP TABLE IF EXISTS `tb_user_copy`;$$$
CREATE TABLE `tb_user_copy`  (
 `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
 PRIMARY KEY (`id`) USING BTREE,
 UNIQUE INDEX `username-unique`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;$$$
SET FOREIGN_KEY_CHECKS = 1;$$$