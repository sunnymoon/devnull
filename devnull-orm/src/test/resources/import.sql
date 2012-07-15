insert into author (id, firstName, lastName) values (1, 'Thomas','Keller')
insert into author (id, firstName, lastName) values (2, 'David','Chang')
insert into author (id, firstName, lastName) values (3, 'Harold','McGee')

-- keller's books
insert into book (id, author_id, title) values (1, 1, 'Ad Hoc at Home')
insert into book (id, author_id, title) values (2, 1, 'Bouchon')
insert into book (id, author_id, title) values (3, 1, 'Under Pressure: Cooking Sous Vide')
insert into book (id, author_id, title) values (4, 1, 'The French Laundry Cookbook')

-- chang's books
insert into book (id, author_id, title) values (5, 2, 'Momofuku')

-- mcgee's books
insert into book (id, author_id, title) values (6, 3, 'On Food and Cooking: The Science and Lore of the Kitchen')
insert into book (id, author_id, title) values (7, 3, 'The Curious Cook: More Kitchen Science and Lore')
