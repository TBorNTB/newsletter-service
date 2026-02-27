CREATE TABLE IF NOT EXISTS subscriber
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    email              VARCHAR(50)  NOT NULL,
    email_frequency    VARCHAR(50)  NOT NULL,
    created_at         DATETIME(6),
    active             TINYINT(1)   NOT NULL,
    chasing_popularity TINYINT(1)   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_subscriber_email UNIQUE (email),
    INDEX idx_subscriber_email_frequency (email_frequency)
    );

CREATE TABLE IF NOT EXISTS mailcategory
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    subscriber_id BIGINT      NOT NULL,
    category_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_mailcategory_subscriber FOREIGN KEY (subscriber_id) REFERENCES subscriber (id)
    );