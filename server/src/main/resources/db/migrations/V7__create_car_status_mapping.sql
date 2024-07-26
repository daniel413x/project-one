CREATE TABLE car_status_mapping (
    car_id INT,
    status_id INT,
    PRIMARY KEY (car_id, status_id),
    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES car_statuses(id) ON DELETE CASCADE
);