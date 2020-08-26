USE `morn_test`;
TRUNCATE `test_user`;
TRUNCATE `test_commodity`;
INSERT INTO `test_user` (`id`, `username`, `password`) VALUE (1, 'admin', 'bmeB4000');
INSERT INTO `test_commodity`(`id`, `name`, `price`, `type`) VALUE (1, '肉松蛋糕', 4, 1);
INSERT INTO `test_commodity`(`id`, `name`, `price`, `type`) VALUE (2, '肉松蛋挞', 4, 2);
INSERT INTO `test_commodity`(`id`, `name`, `price`, `type`) VALUE (3, '蜂蜜蛋糕', 5, 1);
INSERT INTO `test_commodity`(`id`, `name`, `price`, `type`) VALUE (4, '蜂蜜蛋挞', 5, 2);