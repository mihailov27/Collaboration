CREATE TYPE SEX AS ENUM ('male', 'female');

DROP TABLE IF EXISTS employees;

CREATE TABLE IF NOT EXISTS employees (
    id                                      SERIAL PRIMARY KEY,
    first_name                              VARCHAR(40) NOT NULL,
    last_name                               VARCHAR(40) NOT NULL,
    job_position                            VARCHAR(70) NOT NULL,
    date_of_birth                           DATE NOT NULL,
    gender                                  VARCHAR(6) NOT NULL,
    email                                   VARCHAR(40) NOT NULL,
    creation_date                           TIMESTAMP NOT NULL,
    last_update_date                        TIMESTAMP,
    profile_image                           VARCHAR(50),
    CONSTRAINT unique_email_address         UNIQUE(email),
    CONSTRAINT check_gender                 CHECK(gender IN ('male','female'))
);

ALTER TABLE employees SET owner TO test;

GRANT ALL PRIVILEGES ON SCHEMA public TO test;

GRANT ALL PRIVILEGES ON employees TO test;


