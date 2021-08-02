-- public.accounts definition

-- Drop table

-- DROP TABLE public.accounts;

CREATE TABLE public.accounts (
	accid int4 NOT NULL DEFAULT nextval('accounts_id_seq'::regclass),
	nicname varchar NOT NULL,
	accountowner int4 NOT NULL,
	balance float8 NOT NULL,
	isapproved bool NULL,
	CONSTRAINT accounts_pkey PRIMARY KEY (accid)
);


-- public.employees definition

-- Drop table

-- DROP TABLE public.employees;

CREATE TABLE public.employees (
	id serial NOT NULL,
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	isemployee bool NOT NULL,
	CONSTRAINT employees_pkey PRIMARY KEY (id)
);


-- public.transfers definition

-- Drop table

-- DROP TABLE public.transfers;

CREATE TABLE public.transfers (
	transferid int4 NOT NULL DEFAULT nextval('transfers_id_seq'::regclass),
	senderid int4 NULL,
	recipientid int4 NULL,
	senderaccid int4 NOT NULL,
	recipientaccid int4 NOT NULL,
	amount int4 NOT NULL,
	isaccepted bool NULL,
	CONSTRAINT transfers_pkey PRIMARY KEY (transferid)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id serial NOT NULL,
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	accounts _int4 NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);