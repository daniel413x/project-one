CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    vin VARCHAR(17) NOT NULL UNIQUE,
    model_id INT NOT NULL,
    make_id INT NOT NULL,
    color_id INT,
    owner_id INT,
    year INT NOT NULL,
    price DECIMAL(10, 2),
    mileage INT,
    condition VARCHAR(50),
    features TEXT,
    registration_number VARCHAR(50),
    insurance_policy_number VARCHAR(50),
    insurance_expiration TIMESTAMP WITHOUT TIME ZONE,
    registration_expiration TIMESTAMP WITHOUT TIME ZONE,
    last_maintenance_date TIMESTAMP WITHOUT TIME ZONE,
    FOREIGN KEY (model_id) REFERENCES models(id) ON DELETE CASCADE,
    FOREIGN KEY (make_id) REFERENCES makes(id) ON DELETE CASCADE,
    FOREIGN KEY (color_id) REFERENCES colors(id),
    FOREIGN KEY (owner_id) REFERENCES owners(id)
);