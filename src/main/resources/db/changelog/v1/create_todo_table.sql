create table todo
(
    id          uuid not null,
    created_at  timestamp with time zone,
    description varchar(256),
    done_at     timestamp with time zone,
    due_at      timestamp with time zone,
    status      varchar(20)
);