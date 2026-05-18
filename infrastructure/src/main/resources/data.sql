INSERT INTO measurement_units (code, name) VALUES
('GR', 'Gramo'),
('L',  'Litro'),
('M',  'Metro'),
('UN', 'Unidad')
ON CONFLICT (code) DO NOTHING;