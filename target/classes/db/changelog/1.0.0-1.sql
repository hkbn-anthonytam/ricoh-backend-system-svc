CREATE TABLE language (
    language_code       VARCHAR(10) PRIMARY KEY NOT NULL,
    name                VARCHAR(100),
    created_date        TIMESTAMPTZ     NOT NULL,     
    created_by          VARCHAR(20)     NOT NULL,
    updated_date        TIMESTAMPTZ     NOT NULL,     
    updated_by          VARCHAR(20)     NOT NULL
);

CREATE TABLE district (
    district_id         INT PRIMARY KEY NOT NULL,
    created_date        TIMESTAMPTZ     NOT NULL,     
    created_by          VARCHAR(20)     NOT NULL,
    updated_date        TIMESTAMPTZ     NOT NULL,     
    updated_by          VARCHAR(20)     NOT NULL
);

CREATE TABLE district_name (
    district_name_id    INT PRIMARY KEY NOT NULL,
    district_id         INT             references district(district_id),
    language_code       VARCHAR(10)     REFERENCES language(language_code),
    name                VARCHAR(100),
    created_date        TIMESTAMPTZ     NOT NULL,     
    created_by          VARCHAR(20)     NOT NULL,
    updated_date        TIMESTAMPTZ     NOT NULL,     
    updated_by          VARCHAR(20)     NOT NULL
);


CREATE TABLE estate (
    estate_id           INT PRIMARY KEY NOT NULL,
    completion_year     VARCHAR(50),
    location_id         INT             REFERENCES district(district_id),
    site_area           NUMERIC(6,2),
    no_of_rental_blocks INT,
    total_rental_flats  INT,
    created_date        TIMESTAMPTZ     NOT NULL,     
    created_by          VARCHAR(20)     NOT NULL,
    updated_date        TIMESTAMPTZ     NOT NULL,     
    updated_by          VARCHAR(20)     NOT NULL
);

CREATE TABLE estate_name (
    estate_name_id      INT PRIMARY KEY NOT NULL,
    estate_id           INT             REFERENCES estate(estate_id),
    language_code       VARCHAR(10)     REFERENCES language(language_code),
    name                VARCHAR(100),
    created_date        TIMESTAMPTZ     NOT NULL,     
    created_by          VARCHAR(20)     NOT NULL,
    updated_date        TIMESTAMPTZ     NOT NULL,     
    updated_by          VARCHAR(20)     NOT NULL
);

