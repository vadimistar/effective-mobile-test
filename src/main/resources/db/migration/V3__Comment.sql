CREATE TABLE `em-test-db`.comments
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    task_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    text      TEXT   NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

ALTER TABLE `em-test-db`.comments
    ADD CONSTRAINT FK_COMMENTS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES `em-test-db`.users (id);

ALTER TABLE `em-test-db`.comments
    ADD CONSTRAINT FK_COMMENTS_ON_TASK FOREIGN KEY (task_id) REFERENCES `em-test-db`.tasks (id);