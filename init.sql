create table public.users
(
    id         bigint                              not null
        constraint user_pkey
            primary key,
    user_name  varchar(255),
    voucher    varchar(255),
    phone      varchar(20),
    pic        varchar(512),
    created_at timestamp default CURRENT_TIMESTAMP not null,
    updated_at timestamp default CURRENT_TIMESTAMP,
    used       smallint  default 1
        constraint chk_used
            check (used = ANY (ARRAY [0, 1, 2]))
);

comment on table public.users is '用户表';

comment on column public.users.user_name is '用户名';

comment on column public.users.voucher is '登录凭证';

comment on column public.users.phone is '手机号';

comment on column public.users.pic is '用户头像URL';

comment on column public.users.created_at is '创建时间';

comment on column public.users.updated_at is '修改时间';

comment on column public.users.used is '账户状态: 0-注销 1-可用 2-停用';

alter table public.users
    owner to postgres;

create index idx_user_phone
    on public.users (phone);

create table public.execution_log
(
    id              serial
        primary key,
    class_name      varchar(255) not null,
    method_name     varchar(255) not null,
    execution_time  bigint       not null,
    created_at      timestamp default CURRENT_TIMESTAMP,
    sql_query_time  integer,
    sql_update_time integer,
    system_info     varchar(64),
    validator_time  bigint,
    sql_insert_time integer,
    total_sql_times integer
);

comment on table public.execution_log is '方法执行日志记录表';

comment on column public.execution_log.class_name is '类名';

comment on column public.execution_log.method_name is '方法名';

comment on column public.execution_log.execution_time is '执行时间(ms)';

comment on column public.execution_log.created_at is '记录创建时间';

alter table public.execution_log
    owner to postgres;

create index exec_time
    on public.execution_log (execution_time);

create table public.execution_log_history
(
    id          bigint,
    method_name varchar(128),
    day         varchar(64),
    created_at  timestamp,
    avg_time    bigint,
    max_time    bigint,
    times       integer
);

alter table public.execution_log_history
    owner to postgres;

