CREATE SEQUENCE contact_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE contact
(
    id           BIGSERIAL   NOT NULL,
    first_name   VARCHAR(40) NOT NULL,
    last_name    VARCHAR(40) NOT NULL,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY (id)
)