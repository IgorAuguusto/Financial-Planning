--
-- PostgreSQL database dump
--

-- Dumped from database version 14.8
-- Dumped by pg_dump version 14.8

-- Started on 2023-07-09 17:50:10

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
-- TOC entry 209 (class 1259 OID 16396)
-- Name: categoria; Type: TABLE; Schema: public; Owner: dba
--

CREATE TABLE public.categoria (
    codigo bigint NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.categoria OWNER TO dba;

--
-- TOC entry 210 (class 1259 OID 16399)
-- Name: categoria_codigo_seq; Type: SEQUENCE; Schema: public; Owner: dba
--

CREATE SEQUENCE public.categoria_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_codigo_seq OWNER TO dba;

--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 210
-- Name: categoria_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dba
--

ALTER SEQUENCE public.categoria_codigo_seq OWNED BY public.categoria.codigo;


--
-- TOC entry 211 (class 1259 OID 16400)
-- Name: despesa; Type: TABLE; Schema: public; Owner: dba
--

CREATE TABLE public.despesa (
    codigo bigint NOT NULL,
    descricao character varying(255),
    cod_categoria bigint
);


ALTER TABLE public.despesa OWNER TO dba;

--
-- TOC entry 212 (class 1259 OID 16403)
-- Name: despesa_codigo_seq; Type: SEQUENCE; Schema: public; Owner: dba
--

CREATE SEQUENCE public.despesa_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.despesa_codigo_seq OWNER TO dba;

--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 212
-- Name: despesa_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dba
--

ALTER SEQUENCE public.despesa_codigo_seq OWNED BY public.despesa.codigo;


--
-- TOC entry 213 (class 1259 OID 16404)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: dba
--

CREATE TABLE public.forma_pagamento (
    codigo bigint NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.forma_pagamento OWNER TO dba;

--
-- TOC entry 214 (class 1259 OID 16407)
-- Name: forma_pagamento_codigo_seq; Type: SEQUENCE; Schema: public; Owner: dba
--

CREATE SEQUENCE public.forma_pagamento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forma_pagamento_codigo_seq OWNER TO dba;

--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 214
-- Name: forma_pagamento_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dba
--

ALTER SEQUENCE public.forma_pagamento_codigo_seq OWNED BY public.forma_pagamento.codigo;


--
-- TOC entry 215 (class 1259 OID 16408)
-- Name: investimento; Type: TABLE; Schema: public; Owner: dba
--

CREATE TABLE public.investimento (
    codigo bigint NOT NULL,
    objetivo character varying(255),
    estrategia character varying(255),
    nome character varying(255),
    valor_investido numeric(10,2),
    posicao character varying(255),
    rendimento_bruto numeric(10,2),
    rentabilidade numeric(5,2),
    vencimento date
);


ALTER TABLE public.investimento OWNER TO dba;

--
-- TOC entry 216 (class 1259 OID 16413)
-- Name: investimento_codigo_seq; Type: SEQUENCE; Schema: public; Owner: dba
--

CREATE SEQUENCE public.investimento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.investimento_codigo_seq OWNER TO dba;

--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 216
-- Name: investimento_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dba
--

ALTER SEQUENCE public.investimento_codigo_seq OWNED BY public.investimento.codigo;


--
-- TOC entry 217 (class 1259 OID 16414)
-- Name: orcamento; Type: TABLE; Schema: public; Owner: dba
--

CREATE TABLE public.orcamento (
    mes_ano date NOT NULL,
    cod_despesa bigint NOT NULL,
    data_despesa date,
    data_pagamento date,
    cod_forma_pagamento bigint,
    valor numeric(10,2),
    situacao boolean
);


ALTER TABLE public.orcamento OWNER TO dba;

--
-- TOC entry 218 (class 1259 OID 16417)
-- Name: renda; Type: TABLE; Schema: public; Owner: dba
--

CREATE TABLE public.renda (
    codigo bigint NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.renda OWNER TO dba;

--
-- TOC entry 219 (class 1259 OID 16420)
-- Name: renda_codigo_seq; Type: SEQUENCE; Schema: public; Owner: dba
--

CREATE SEQUENCE public.renda_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.renda_codigo_seq OWNER TO dba;

--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 219
-- Name: renda_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dba
--

ALTER SEQUENCE public.renda_codigo_seq OWNED BY public.renda.codigo;


--
-- TOC entry 220 (class 1259 OID 16421)
-- Name: renda_mensal; Type: TABLE; Schema: public; Owner: dba
--

CREATE TABLE public.renda_mensal (
    cod_renda bigint NOT NULL,
    data date NOT NULL,
    valor numeric(10,2)
);


ALTER TABLE public.renda_mensal OWNER TO dba;

--
-- TOC entry 3192 (class 2604 OID 16424)
-- Name: categoria codigo; Type: DEFAULT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.categoria ALTER COLUMN codigo SET DEFAULT nextval('public.categoria_codigo_seq'::regclass);


--
-- TOC entry 3193 (class 2604 OID 16425)
-- Name: despesa codigo; Type: DEFAULT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.despesa ALTER COLUMN codigo SET DEFAULT nextval('public.despesa_codigo_seq'::regclass);


--
-- TOC entry 3194 (class 2604 OID 16426)
-- Name: forma_pagamento codigo; Type: DEFAULT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.forma_pagamento ALTER COLUMN codigo SET DEFAULT nextval('public.forma_pagamento_codigo_seq'::regclass);


--
-- TOC entry 3195 (class 2604 OID 16427)
-- Name: investimento codigo; Type: DEFAULT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.investimento ALTER COLUMN codigo SET DEFAULT nextval('public.investimento_codigo_seq'::regclass);


--
-- TOC entry 3196 (class 2604 OID 16428)
-- Name: renda codigo; Type: DEFAULT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.renda ALTER COLUMN codigo SET DEFAULT nextval('public.renda_codigo_seq'::regclass);


--
-- TOC entry 3198 (class 2606 OID 16430)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3200 (class 2606 OID 16432)
-- Name: despesa despesa_pkey; Type: CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3202 (class 2606 OID 16434)
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3204 (class 2606 OID 16436)
-- Name: investimento investimento_pkey; Type: CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.investimento
    ADD CONSTRAINT investimento_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3206 (class 2606 OID 16438)
-- Name: orcamento orcamento_pkey; Type: CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_pkey PRIMARY KEY (mes_ano, cod_despesa);


--
-- TOC entry 3210 (class 2606 OID 16440)
-- Name: renda_mensal renda_mensal_pkey; Type: CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.renda_mensal
    ADD CONSTRAINT renda_mensal_pkey PRIMARY KEY (cod_renda, data);


--
-- TOC entry 3208 (class 2606 OID 16442)
-- Name: renda renda_pkey; Type: CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.renda
    ADD CONSTRAINT renda_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3211 (class 2606 OID 16443)
-- Name: despesa despesa_cod_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_cod_categoria_fkey FOREIGN KEY (cod_categoria) REFERENCES public.categoria(codigo);


--
-- TOC entry 3212 (class 2606 OID 16448)
-- Name: orcamento orcamento_cod_despesa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_cod_despesa_fkey FOREIGN KEY (cod_despesa) REFERENCES public.despesa(codigo);


--
-- TOC entry 3213 (class 2606 OID 16453)
-- Name: orcamento orcamento_cod_forma_pagamento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_cod_forma_pagamento_fkey FOREIGN KEY (cod_forma_pagamento) REFERENCES public.forma_pagamento(codigo);


--
-- TOC entry 3214 (class 2606 OID 16458)
-- Name: renda_mensal renda_mensal_cod_renda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: dba
--

ALTER TABLE ONLY public.renda_mensal
    ADD CONSTRAINT renda_mensal_cod_renda_fkey FOREIGN KEY (cod_renda) REFERENCES public.renda(codigo);


-- Completed on 2023-07-09 17:50:10

--
-- PostgreSQL database dump complete
--

