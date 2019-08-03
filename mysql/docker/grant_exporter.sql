CREATE USER 'exporter'@'localhost' IDENTIFIED BY 'eip_exporter_passwd' WITH MAX_USER_CONNECTIONS 3;
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'exporter'@'localhost';
CREATE USER 'exporter'@'127.0.0.1' IDENTIFIED BY 'eip_exporter_passwd' WITH MAX_USER_CONNECTIONS 3;
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'exporter'@'127.0.0.1';
flush privileges;
