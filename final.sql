create database project_sub3;
use project_sub3;

insert  
into schedule(title, description, seq, name, startDate, events, createTime) 
values('#{title}', '#{description}', 1, '#{name}', '#{startDate}', null, '#{createTime}');

select * from member;
update member set type="admin" where seq=27;
select * from member where email like '% %' order by seq desc;
select * from post where title like '% %' or description like '% %';
select 
postNo, title, name, tags, thumbnail, createDate 
from Post 
order by postNo desc;
        
drop table if exists schedule;
drop table if exists Member;
drop table if exists post;

create table Member(
seq int primary key auto_increment,
email varchar(50) unique ,
password text not null,
name varchar(50) not null,
age int not null,
gender varchar(20) not null,
phone varchar(100) not null,
create_at varchar(100) not null,
type varchar(20) not null
);

create table Schedule(
scdNo int primary key auto_increment,
seq int,
title varchar(100),
country text,
name varchar(50),
startDate varchar(50),
description text,
createTime varchar(50),
updateTime varchar(50),
events json,
views int default 0,
likes int default 0
);

create table post(
postNo int primary key auto_increment,
seq int not null,
title varchar(100) not null,
description text,
updateDate varchar(50),
createDate varchar(50) not null,
name varchar(50) not null,
content text,
tags text,
thumbnail text,
views int default 0,
likes int default 0
);



select * from post ;
update post set views=50 where postNo=7;
select * from schedule;
select * from schedule2;
insert into Member
value("admin2", "admin", "admin", 0, "man", 010, now(),"admin");
insert into Member
value(3,"user1", "user1", "user1", 0, "man", 010, now(),"user");
update Member set password="123", age=2 where seq=2;
select now();
select * from post;
update post set views=views+1 where postNo=1;

select postNo, title, name, tags, thumbnail, createDate from Post where title like '%' ORDER BY postNo DESC LIMIT 10,5; 
select * from post;
#insert into Schedule value(0,"admin","2017-09-10","2017-10-10",
SELECT * FROM Member ORDER BY create_at DESC;