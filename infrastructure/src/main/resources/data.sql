INSERT INTO measurement_unit (code, name) VALUES
('GR', 'Gramo'),
('L',  'Litro'),
('M',  'Metro'),
('UN', 'Unidad')
ON CONFLICT (name) DO NOTHING;

INSERT INTO product_type (name) VALUES
('Ingrediente'),
('paqueteria'),
('Servicio')
ON CONFLICT (name) DO NOTHING;

INSERT INTO product_currency (name, symbol, code, description)
VALUES ('Pesos', '$', 'COP', 'Pesos Colombianos')
ON CONFLICT (code) DO NOTHING;