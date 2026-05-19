INSERT INTO measurement_unit (code, name) VALUES
('GR', 'Gramo'),
('L',  'Litro'),
('M',  'Metro'),
('UN', 'Unidad')
ON CONFLICT (code) DO NOTHING;

INSERT INTO product_type (name) VALUES
('Ingrediente'),
('paqueteria'),
('Servicio')
ON CONFLICT (code) DO NOTHING;