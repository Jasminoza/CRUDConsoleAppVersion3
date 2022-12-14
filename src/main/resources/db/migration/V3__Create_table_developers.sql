create TABLE developers (
    id integer PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    firstName varchar(255) NOT NULL,
    lastName varchar(255) NOT NULL,
    specialty integer NOT NULL,
    status varchar(30) NOT NULL,
    CONSTRAINT developers_fk0
    FOREIGN KEY (specialty) REFERENCES specialties(id)
);