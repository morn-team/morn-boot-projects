USE `morn_test`;
TRUNCATE `test_user`;
TRUNCATE `test_commodity`;
INSERT INTO `test_user` (`id`, `username`, `password`) VALUE (1, 'admin', 'bmeB4000');
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (1, '肉松蛋糕', '肉松', 4, 1);
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (2, '肉松蛋挞', '肉松', 4, 2);
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (3, '蜂蜜蛋糕', '蜂蜜', 5, 1);
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (4, '蜂蜜蛋挞', '蜂蜜', 5, 2);