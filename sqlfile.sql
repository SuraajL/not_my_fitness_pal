--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: food_entries; Type: TABLE; Schema: public;
--

CREATE TABLE public.food_entries (
    id integer NOT NULL,
    person_id integer NOT NULL,
    name character varying(255),
    meal_type character varying(255),
    notes text,
    calories integer,
    week integer,
    day character varying(255)
);

--
-- Name: food_entries_id_seq; Type: SEQUENCE; Schema: public;
--

CREATE SEQUENCE public.food_entries_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;




--
-- Name: food_entries_id_seq; Type: SEQUENCE OWNED BY; Schema: public;
--

ALTER SEQUENCE public.food_entries_id_seq OWNED BY public.food_entries.id;


--
-- Name: people; Type: TABLE; Schema: public;
--

CREATE TABLE public.people (
    id integer NOT NULL,
    name character varying(255),
    age integer,
    height_in_cm numeric(4,1),
    weight_in_kg numeric(4,1),
    calorie_target integer
);

--
-- Name: people_id_seq; Type: SEQUENCE; Schema: public;
--

CREATE SEQUENCE public.people_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;




--
-- Name: people_id_seq; Type: SEQUENCE OWNED BY; Schema: public;
--

ALTER SEQUENCE public.people_id_seq OWNED BY public.people.id;


--
-- Name: food_entries id; Type: DEFAULT; Schema: public;
--

ALTER TABLE ONLY public.food_entries ALTER COLUMN id SET DEFAULT nextval('public.food_entries_id_seq'::regclass);


--
-- Name: people id; Type: DEFAULT; Schema: public;
--

ALTER TABLE ONLY public.people ALTER COLUMN id SET DEFAULT nextval('public.people_id_seq'::regclass);


--
-- Data for Name: food_entries; Type: TABLE DATA; Schema: public;
--

COPY public.food_entries (id, person_id, name, meal_type, notes, calories, week, day) FROM stdin;
1	1	toast	BREAKFAST	Kingsmill 50/50 bread with butter	60	1	MONDAY
2	1	cereal	BREAKFAST	Curiously Cinnamon	400	1	TUESDAY
3	3	toast	BREAKFAST	Kingsmill 50/50 bread with nutella	60	1	MONDAY
4	3	cereal	BREAKFAST	Curiously Cinnamon	400	1	TUESDAY
5	3	tuna & sweetcorn sandwich	LUNCH	Kingsmill 50/50 bread with tuna, mayo and sweetcorn 	200	1	MONDAY
6	3	greek yoghurt with grapes	LUNCH	greek honey yoghurt with black grapes	150	1	TUESDAY
7	3	fish and chips	DINNER	Cod fish and fat chips	600	1	MONDAY
8	3	spaghetti bolognese	DINNER	Minced meat, tomato sauce	650	1	TUESDAY
9	4	cereal	BREAKFAST	bowl of cornflakes	350	1	MONDAY
10	4	cereal	BREAKFAST	bowl of cornflakes	350	1	TUESDAY
11	4	sandwich	LUNCH	cheese and cucumber sandwich with white bread	400	1	MONDAY
12	4	sandwich	LUNCH	chicken sandwich with white bread	500	1	TUESDAY
13	4	burger and chips	DINNER	quarter pounder chicken burger with chips	700	1	MONDAY
14	4	fish and chips	DINNER	Cod fish and fat chips	600	1	TUESDAY
15	4	packet of crisps	SNACK	thai sweet chilli sensations	100	1	MONDAY
16	4	fridge raiders	SNACK	chicken fridge raiders	100	1	TUESDAY
17	5	cereal and milk	BREAKFAST	frosties	250	1	MONDAY
18	5	cheese toastie	LUNCH	cheddar	500	1	MONDAY
19	5	roast pork noodle soup	DINNER	takeaway	650	1	MONDAY
20	5	avocado on toast	BREAKFAST	sliced avocado on brown bread	200	1	TUESDAY
21	5	chilli con carne	DINNER	minced beef and rice	700	1	TUESDAY
22	5	ice cream	SNACK	vanilla flavour	200	1	TUESDAY
23	2	pan au chocolat	BREAKFAST	Lidl fresh bakery	150	1	MONDAY
24	2	lasagna	LUNCH	Lidl lasagna	350	1	MONDAY
25	2	lamb biryani	DINNER	homemade	600	1	MONDAY
26	2	weetabix	BREAKFAST	2-3 pieces with heated milk	200	1	TUESDAY
27	2	bao buns and dumplings	LUNCH	bao buns and dumplings	300	1	TUESDAY
28	2	spaghetti bolognese	DINNER	homemade	200	1	TUESDAY
\.


--
-- Data for Name: people; Type: TABLE DATA; Schema: public;
--

COPY public.people (id, name, age, height_in_cm, weight_in_kg, calorie_target) FROM stdin;
1	Sarina	22	167.0	58.0	2000
2	Aaron	23	175.4	65.0	2500
3	Nasir	23	172.0	64.0	3000
4	Suraaj	23	170.0	80.0	2000
5	Marcy	23	157.0	47.0	2000
\.


--
-- Name: food_entries_id_seq; Type: SEQUENCE SET; Schema: public;
--

SELECT pg_catalog.setval('public.food_entries_id_seq', 28, true);


--
-- Name: people_id_seq; Type: SEQUENCE SET; Schema: public;
--

SELECT pg_catalog.setval('public.people_id_seq', 5, true);


--
-- Name: food_entries food_entries_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.food_entries
    ADD CONSTRAINT food_entries_pkey PRIMARY KEY (id);


--
-- Name: people people_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_pkey PRIMARY KEY (id);


--
-- Name: food_entries food_entries_person_id_fkey; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY public.food_entries
    ADD CONSTRAINT food_entries_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.people(id);


--
-- PostgreSQL database dump complete
--

