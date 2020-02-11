drop table if exists movie_actor_res ;
drop table if exists movie_category_res ;
drop table if exists movie_director_res ;
drop table if exists movie_producer_res ;
drop table if exists movie_music_comp_res ;
drop table if exists movie_prod_house_res ;
drop table if exists movie_language_res ;

drop table if exists actors;
drop table if exists producers;
drop table if exists production_houses;
drop table if exists directors;
drop table if exists music_composers;
drop table if exists languages;
drop table if exists categories;

drop table if exists movies;

create table movies
(
m_id int not null auto_increment primary key,
title varchar(200)  not null,
release_date date not null,
duration varchar(200) not null,
budget varchar(200) not null,
collection varchar(200) not null,
summary varchar(9000) not null,
category_count int not null
);

create table actors (
a_id int not null auto_increment primary key,
a_name varchar(200) not null
);

create table producers (
p_id int not null auto_increment primary key,
p_name varchar(200) not null
);

create table production_houses (
ph_id int not null auto_increment primary key,
ph_name varchar(200) not null
);

create table directors (
d_id int not null auto_increment primary key,
d_name varchar(200) not null
);

create table music_composers (
mc_id int not null auto_increment primary key,
mc_name varchar(200) not null
);

create table languages (
l_id int not null auto_increment primary key,
l_name varchar(200) not null
);

create table categories (
c_id int not null auto_increment primary key,
c_name varchar(200) not null
);

create table movie_actor_res (
pid int not null auto_increment primary key,
m_id int not null,
a_id int not null,
foreign key (m_id) references movies(m_id) on delete cascade,
foreign key (a_id) references actors(a_id) on delete cascade
);

create table movie_category_res (
pid int not null auto_increment primary key,
m_id int not null,
c_id int not null,
foreign key (m_id) references movies(m_id) on delete cascade,
foreign key (c_id) references categories(c_id) on delete cascade
);

create table movie_director_res (
pid int not null auto_increment primary key,
m_id int not null,
d_id int not null,
foreign key (m_id) references movies(m_id) on delete cascade,
foreign key (d_id) references directors(d_id) on delete cascade
);

create table movie_producer_res (
pid int not null auto_increment primary key,
m_id int not null,
p_id int not null,
foreign key (m_id) references movies(m_id) on delete cascade,
foreign key (p_id) references producers(p_id) on delete cascade
);

create table movie_music_comp_res (
pid int not null auto_increment primary key,
m_id int not null,
mc_id int not null,
foreign key (m_id) references movies(m_id) on delete cascade,
foreign key (mc_id) references music_composers(mc_id) on delete cascade
);

create table movie_prod_house_res (
pid int not null auto_increment primary key,
m_id int not null,
ph_id int not null,
foreign key (m_id) references movies(m_id) on delete cascade,
foreign key (ph_id) references production_houses(ph_id) on delete cascade
);

create table movie_language_res (
pid int not null auto_increment primary key,
m_id int not null,
l_id int not null,
foreign key (m_id) references movies(m_id) on delete cascade,
foreign key (l_id) references languages(l_id) on delete cascade
);