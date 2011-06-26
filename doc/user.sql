--
-- Creates obliquid_db user with password abcd
--

GRANT USAGE ON *.* TO 'obliquid_db'@'localhost';
DROP USER 'obliquid_db'@'localhost';
CREATE USER 'obliquid_db'@'localhost' IDENTIFIED BY 'abcd';

INSERT INTO `mysql`.`db` (`Host`, `Db`, `User`, `Select_priv`, `Insert_priv`, `Update_priv`, `Delete_priv`, `Create_priv`, `Drop_priv`, `Grant_priv`, `References_priv`, `Index_priv`, `Alter_priv`, `Create_tmp_table_priv`, `Lock_tables_priv`, `Create_view_priv`, `Show_view_priv`, `Create_routine_priv`, `Alter_routine_priv`, `Execute_priv`, `Event_priv`, `Trigger_priv`) VALUES ('localhost', 'obliquid_db1', 'obliquid_db', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N'), ('localhost', 'obliquid_db2', 'obliquid_db', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N');

FLUSH PRIVILEGES;

