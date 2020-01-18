CREATE SEQUENCE public.customer_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_customer
(
    id                    bigint DEFAULT NEXTVAL('public.customer_id_seq'::regclass) NOT NULL
        constraint customer_pkey
            primary key,
    is_active             boolean                                                    not null,
    created               timestamp,
    updated               timestamp,
    address               varchar(255),
    email                 varchar(255),
    first_name            varchar(255)                                               not null,
    identification_number varchar(255)                                               not null,
    last_name             varchar(255)                                               not null,
    phone                 varchar(255),
    user_id               bigint
);

CREATE SEQUENCE public.hotel_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_hotel
(
    id        bigint DEFAULT NEXTVAL('public.hotel_id_seq'::regclass) NOT NULL
        constraint hotel_pkey
            primary key,
    is_active boolean                                                 not null,
    created   timestamp,
    updated   timestamp,
    email     varchar(255),
    name      varchar(255)                                            not null
);
CREATE SEQUENCE public.location_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_location
(
    id          bigint DEFAULT NEXTVAL('public.location_id_seq'::regclass) NOT NULL
        constraint location_pkey
            primary key,
    is_active   boolean                                                    not null,
    created     timestamp,
    updated     timestamp,
    address     varchar(255),
    city        varchar(255),
    name        varchar(255)                                               not null,
    phone       varchar(255),
    postal_code varchar(255),
    hotel_id    bigint                                                     not null
        constraint idx_location_hotel_id
            references hotelo_hotel
);

CREATE SEQUENCE public.cart_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_cart
(
    id          bigint DEFAULT NEXTVAL('public.cart_id_seq'::regclass) NOT NULL
        constraint cart_pkey
            primary key,
    is_active   boolean                                                not null,
    created     timestamp,
    updated     timestamp,
    discount    numeric(19, 2)                                         not null,
    subtotal    numeric(19, 2)                                         not null,
    tax         numeric(19, 2)                                         not null,
    total       numeric(19, 2)                                         not null,
    location_id bigint                                                 not null
        constraint idx_cart_location_id
            references hotelo_location,
    order_id    bigint
);

CREATE SEQUENCE public.section_id_seq
    INCREMENT BY 1
    START WITH 1;

create table if not exists hotelo_section
(
    id          bigint DEFAULT NEXTVAL('public.section_id_seq'::regclass) NOT NULL
        constraint section_pkey
            primary key,
    is_active   boolean                                                   not null,
    created     timestamp,
    updated     timestamp,
    name        varchar(255)                                              not null,
    location_id bigint                                                    not null
        constraint idx_section_location_id
            references hotelo_location
);

CREATE SEQUENCE public.room_id_seq
    INCREMENT BY 1
    START WITH 1;

create table if not exists hotelo_room
(
    id         bigint DEFAULT NEXTVAL('public.room_id_seq'::regclass) NOT NULL
        constraint room_pkey
            primary key,
    is_active  boolean                                                not null,
    created    timestamp,
    updated    timestamp,
    bed_number integer                                                not null
        constraint room_bed_number_check
            check (bed_number >= 0),
    name       varchar(255)                                           not null,
    status     varchar(255),
    section_id bigint
        constraint idx_room_section_id
            references hotelo_section
);


CREATE SEQUENCE public.cart_item_id_seq
    INCREMENT BY 1
    START WITH 1;

create table if not exists hotelo_cart_item
(
    id         bigint DEFAULT NEXTVAL('public.cart_item_id_seq'::regclass) NOT NULL
        constraint cart_item_pkey
            primary key,
    is_active  boolean                                                     not null,
    created    timestamp,
    updated    timestamp,
    end_date   date,
    name       varchar(255)                                                not null,
    quantity   integer                                                     not null,
    start_date date                                                        not null,
    tax        numeric(14, 2)                                              not null
        constraint cart_item_tax_check
            check (tax >= (0)::numeric),
    total      numeric(14, 2)                                              not null
        constraint cart_item_total_check
            check (total >= (0)::numeric),
    unit_price numeric(14, 2)                                              not null
        constraint cart_item_unit_price_check
            check (unit_price >= (0)::numeric),
    cart_id    bigint                                                      not null
        constraint idx_cart_item_cart_id
            references hotelo_cart,
    room_id    bigint                                                      not null
        constraint idx_cart_item_room_id
            references hotelo_room
);
CREATE SEQUENCE public.order_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_order
(
    id           bigint DEFAULT NEXTVAL('order_id_seq'::regclass) NOT NULL
        constraint order_pkey
            primary key,
    is_active    boolean                                          not null,
    created      timestamp,
    updated      timestamp,
    discount     numeric(19, 2)                                   not null,
    issue_date   timestamp                                        not null,
    cart_id      bigint                                           not null
        constraint idx_order_cart_id
            references hotelo_cart,
    order_type   varchar(255)                                     not null,
    order_number varchar(255)                                     not null,
    paid_status  varchar(255)                                     not null,
    subtotal     numeric(19, 2)                                   not null,
    tax          numeric(19, 2)                                   not null,
    total        numeric(19, 2)                                   not null,
    customer_id  bigint                                           not null
        constraint idx_order_customer_id
            references hotelo_customer
);
CREATE SEQUENCE public.order_detail_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_order_detail
(
    id           bigint DEFAULT NEXTVAL('order_detail_id_seq'::regclass) NOT NULL
        constraint order_detail_pkey
            primary key,
    is_active    boolean                                                 not null,
    created      timestamp,
    updated      timestamp,
    name         varchar(255)                                            not null,
    quantity     integer                                                 not null,
    tax          numeric(14, 2)                                          not null
        constraint order_detail_tax_check
            check (tax >= (0)::numeric),
    total        numeric(14, 2)                                          not null
        constraint order_detail_total_check
            check (total >= (0)::numeric),
    unit_price   numeric(14, 2)                                          not null
        constraint order_detail_unit_price_check
            check (unit_price >= (0)::numeric),
    cart_item_id bigint                                                  not null
        constraint idx_order_detail_cart_item_id
            references hotelo_cart_item,
    order_id     bigint                                                  not null
        constraint idx_order_detail_order_id
            references hotelo_order
);
CREATE SEQUENCE public.price_id_seq
    INCREMENT BY 1
    START WITH 1;

create table if not exists hotelo_price
(
    id          bigint DEFAULT NEXTVAL('price_id_seq'::regclass) NOT NULL
        constraint price_pkey
            primary key,
    is_active   boolean                                          not null,
    created     timestamp,
    updated     timestamp,
    name        varchar(255)                                     not null,
    price       numeric(14, 2)                                   not null
        constraint price_price_check
            check (price >= (0)::numeric),
    price_order integer                                          not null,
    room_id     bigint                                           not null
        constraint idx_price_room_id
            references hotelo_room
);
CREATE SEQUENCE public.role_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_role
(
    id          bigint                DEFAULT NEXTVAL('role_id_seq'::regclass) NOT NULL
        constraint role_pkey
            primary key,
    is_active   boolean      not null default true,
    created     timestamp,
    updated     timestamp,
    description varchar(500),
    name        varchar(255) not null
);

CREATE SEQUENCE public.token_id_seq
    INCREMENT BY 1
    START WITH 1;
create table if not exists hotelo_token
(
    id           bigint DEFAULT NEXTVAL('token_id_seq'::regclass) NOT NULL
        constraint token_pkey
            primary key,
    is_active    boolean                                          not null,
    created      timestamp,
    updated      timestamp,
    data         text                                             not null,
    expired      timestamp                                        not null,
    token_status varchar(255)                                     not null,
    token_type   varchar(255)                                     not null,
    user_id      bigint                                           not null
);
CREATE SEQUENCE public.user_id_seq
    INCREMENT BY 1
    START WITH 1;
CREATE TABLE IF NOT EXISTS hotelo_user
(
    id            bigint DEFAULT NEXTVAL('public.user_id_seq'::regclass) NOT NULL,
    is_active     boolean                                                NOT NULL,
    created       timestamp without time zone,
    updated       timestamp without time zone,
    password      character varying(255),
    is_super_user boolean,
    username      character varying(255)                                 NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT idx_user_username UNIQUE (username)
)
    WITH (
        OIDS= FALSE
    );
CREATE TABLE if not exists hotelo_user_rol
(
    user_id bigint NOT NULL,
    rol_id  bigint NOT NULL,
    CONSTRAINT idx_rol_id FOREIGN KEY (rol_id)
        REFERENCES hotelo_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT idx_user_id FOREIGN KEY (user_id)
        REFERENCES hotelo_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
    WITH (
        OIDS= FALSE
    );
