drop table airports if exists;
create table airports
(
    icao      varchar(255) not null
        primary key,
    city      varchar(255) null,
    country   varchar(255) null,
    iata      varchar(255) null,
    latitude  float        null,
    longitude float        null,
    name      varchar(255) null,
    timezone  varchar(255) null
);

create index city
    on airports (city);

create index iata
    on airports (iata);

