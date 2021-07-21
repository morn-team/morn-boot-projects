USE `morn_test`;
TRUNCATE `test_user`;
TRUNCATE `test_commodity`;
INSERT INTO `test_user` (`id`, `username`, `password`) VALUE (1, 'admin', 'bmeB4000');
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (1, 'PorkFlossCake', 'PorkFloss', 4, 1);
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (2, 'PorkFlossEggTart', 'PorkFloss', 4, 2);
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (3, 'HoneyCake', 'Honey', 5, 1);
INSERT INTO `test_commodity`(`id`, `name`, `ingredients`, `price`, `type`) VALUE (4, 'HoneyEggTart', 'Honey', 5, 2);