CREATE TABLE measurement_unit (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_name_unit UNIQUE (name)
);
CREATE TABLE product_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_name_unit UNIQUE (name)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    unit_amount NOT NULL DOUBLE DEFAULT 0,
    code_measurement_unit VARCHAR(10),
    id_product_type INTEGER,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_name_unit UNIQUE (name),
    CONSTRAINT fk_product_measurementunit FOREIGN KEY (code_measurement_unit)  REFERENCES measurement_unit (code),
    CONSTRAINT fk_product_producttype FOREIGN KEY (id_product_type)  REFERENCES product_type (id)
);
