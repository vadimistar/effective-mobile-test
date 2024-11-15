CREATE TABLE `em-test-db`.users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    `role`   VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE `em-test-db`.users
    ADD CONSTRAINT uc_users_email UNIQUE (email);