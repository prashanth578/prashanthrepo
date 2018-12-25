create table Station (
	stationId varchar(20) primary key, 
	name      varchar(20) not null,
	hdEnabled boolean,
	callSign  varchar(20) not null
);

