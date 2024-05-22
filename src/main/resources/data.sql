DROP TABLE IF EXISTS countries;

CREATE TABLE countries (
    ip_country varchar(20),
    country_name varchar(40),
    country_iso_code varchar(20),
    country_languages varchar(100),
    country_currency varchar(50),
    country_timezones varchar(100),
    country_distance varchar(80),
    country_calls int,
    PRIMARY KEY (ip_country)
);