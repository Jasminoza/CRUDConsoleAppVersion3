create TABLE developers_Skills (
    developer_ID int NOT NULL,
    skill_ID int NOT NULL,
    CONSTRAINT developers_Skills_fk0
    FOREIGN KEY (developer_ID) REFERENCES developers(id),
    CONSTRAINT developers_Skills_fk1
    FOREIGN KEY (skill_ID) REFERENCES skills(id)
);