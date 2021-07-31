create table transfers(
	id serial not null,
	senderid int,
	recipientid int,
	senderaccid int not null,
	recipientaccid int not null,
	amount int not null,
	primary key (id)
);