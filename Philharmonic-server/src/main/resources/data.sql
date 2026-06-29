-- Default roles and users to log in

INSERT INTO role (name, created_at, updated_at, description) VALUES
    ('USER', now(), now(), 'Обычный пользователь – только чтение'),
    ('ADMIN', now(), now(), 'Администратор – создание и редактирование'),
    ('SUPERADMIN', now(), now(), 'Суперадмин – полный доступ')
ON CONFLICT (name) DO NOTHING;

ALTER TABLE role_permissions ADD PRIMARY KEY (role_id, permission);

-- USER (только READ)
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'READ' FROM role WHERE name = 'USER'
ON CONFLICT (role_id, permission) DO NOTHING;

-- ADMIN (CREATE, READ, UPDATE)
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'CREATE' FROM role WHERE name = 'ADMIN'
ON CONFLICT DO NOTHING;
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'READ' FROM role WHERE name = 'ADMIN'
ON CONFLICT DO NOTHING;
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'UPDATE' FROM role WHERE name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- SUPERADMIN (CREATE, READ, UPDATE. DELETE)
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'CREATE' FROM role WHERE name = 'SUPERADMIN'
ON CONFLICT DO NOTHING;
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'READ' FROM role WHERE name = 'SUPERADMIN'
ON CONFLICT DO NOTHING;
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'UPDATE' FROM role WHERE name = 'SUPERADMIN'
ON CONFLICT DO NOTHING;
INSERT INTO role_permissions (role_id, permission)
SELECT id, 'DELETE' FROM role WHERE name = 'SUPERADMIN'
ON CONFLICT DO NOTHING;