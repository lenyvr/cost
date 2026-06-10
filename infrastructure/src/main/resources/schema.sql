CREATE TABLE IF NOT EXISTS measurement_unit (
    code VARCHAR(10) PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT measurement_unit_unique_name UNIQUE (name)
);

CREATE table IF NOT EXISTS product_type (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_type_unique_name UNIQUE (name)
);

CREATE table IF NOT EXISTS product_currency (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    symbol VARCHAR(5) NOT NULL,
    code VARCHAR(10) NOT NULL,
    description VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_currency_unique_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    amount_value NUMERIC(10,2) NOT NULL DEFAULT 0,
    measurement_unit_code VARCHAR(10) not null,
    product_currency_id INTEGER not null,
    product_type_id INTEGER not null,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT product_unique_name UNIQUE (name),
    CONSTRAINT fk_product_measurementunit FOREIGN KEY (measurement_unit_code)  REFERENCES measurement_unit (code),
    CONSTRAINT fk_product_producttype FOREIGN KEY (product_type_id)  REFERENCES product_type (id),
    CONSTRAINT fk_product_productcurrency FOREIGN KEY (product_currency_id)  REFERENCES product_currency (id)
);