insert into SecurityUser (id, openId, firstName, lastName, email, enabled) values (1, 'http://fake.openid.com/bmurray', 'Bill', 'Murray', 'bmurray@ghostbusters.com', true)
insert into SecurityUser (id, openId, firstName, lastName, email, enabled) values (2, 'http://fake.openid.com/hramis', 'Harold', 'Ramis', 'hramis@ghostbusters.com', true)
insert into SecurityUser (id, openId, firstName, lastName, email, enabled) values (3, 'http://fake.openid.com/daykroyd', 'Dan', 'Aykroyd', 'daykroyd@ghostbusters.com', true)

insert into SecurityRole (id, name) values (1, 'ROLE_GUEST')
insert into SecurityRole (id, name) values (2, 'ROLE_USER')
insert into SecurityRole (id, name) values (3, 'ROLE_ADMIN')
insert into SecurityRole (id, name) values (4, 'ROLE_SYSTEM_ADMIN')

insert into SecurityUserRole (userId, roleId) values (1, 2)