CREATE TABLE `em-test-db`.tasks
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255) NULL,
    `description` TEXT         NOT NULL,
    status        VARCHAR(255) NULL,
    priority      VARCHAR(255) NULL,
    creator_id    BIGINT       NOT NULL,
    performer_id  BIGINT NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);

ALTER TABLE `em-test-db`.tasks
    ADD CONSTRAINT FK_TASKS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES `em-test-db`.users (id);

ALTER TABLE `em-test-db`.tasks
    ADD CONSTRAINT FK_TASKS_ON_PERFORMER FOREIGN KEY (performer_id) REFERENCES `em-test-db`.users (id);