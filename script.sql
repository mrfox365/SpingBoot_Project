create user teacher with password 'teacher';
create role teacher_role;
GRANT teacher_role TO teacher;

create sequence students_student_id_seq
    as integer;

alter sequence students_student_id_seq owner to postgres;

grant select, update, usage on sequence students_student_id_seq to teacher_role;

create sequence subjects_subject_id_seq
    as integer;

alter sequence subjects_subject_id_seq owner to postgres;

grant select, update, usage on sequence subjects_subject_id_seq to teacher_role;

create sequence tasks_task_id_seq
    as integer;

alter sequence tasks_task_id_seq owner to postgres;

grant select, update, usage on sequence tasks_task_id_seq to teacher_role;

create sequence task_results_result_id_seq
    as integer;

alter sequence task_results_result_id_seq owner to postgres;

grant select, update, usage on sequence task_results_result_id_seq to teacher_role;

create table student
(
    student_id    integer default nextval('students_student_id_seq'::regclass) not null
        constraint students_pkey
            primary key,
    first_name    varchar(50),
    last_name     varchar(50),
    date_of_birth date,
    group_name    varchar(50)
);

alter table student
    owner to postgres;

alter sequence students_student_id_seq owned by student.student_id;

grant delete, insert, select, update on student to teacher_role;

create table subject
(
    subject_id  integer default nextval('subjects_subject_id_seq'::regclass) not null
        constraint subjects_pkey
            primary key,
    title       varchar(100),
    description text
);

alter table subject
    owner to postgres;

alter sequence subjects_subject_id_seq owned by subject.subject_id;

grant delete, insert, select, update on subject to teacher_role;

create table student_subject
(
    student_id integer not null
        references student,
    subject_id integer not null
        references subject,
    primary key (student_id, subject_id)
);

alter table student_subject
    owner to postgres;

grant delete, insert, select, update on student_subject to teacher_role;

create table task
(
    task_id    integer default nextval('tasks_task_id_seq'::regclass) not null
        constraint tasks_pkey
            primary key,
    title      varchar(100),
    content    text,
    max_score  double precision,
    subject_id integer
        constraint tasks_subject_id_fkey
            references subject
);

alter table task
    owner to postgres;

alter sequence tasks_task_id_seq owned by task.task_id;

grant delete, insert, select, update on task to teacher_role;

create table task_result
(
    result_id  integer default nextval('task_results_result_id_seq'::regclass) not null
        constraint task_results_pkey
            primary key,
    task_id    integer
        constraint task_results_task_id_fkey
            references task,
    student_id integer
        constraint task_results_student_id_fkey
            references student,
    score      double precision                                                not null
);

alter table task_result
    owner to postgres;

alter sequence task_results_result_id_seq owned by task_result.result_id;

grant delete, insert, select, update on task_result to teacher_role;

create function check_score() returns trigger
    language plpgsql
as
$$
DECLARE
    max_score FLOAT;
BEGIN
    -- Отримати максимальну оцінку для завдання
    SELECT t.max_score INTO max_score
    FROM task t
    WHERE t.task_id = NEW.task_id;

    -- Перевірити, чи оцінка в межах допустимого діапазону
    IF NEW.score < 0 OR NEW.score > max_score THEN
        RAISE EXCEPTION 'Score must be between 0 and %', max_score;
    END IF;

    RETURN NEW;
END;
$$;

alter function check_score() owner to postgres;

create trigger check_score_trigger
    before insert or update
    on task_result
    for each row
execute procedure check_score();

create procedure delete_student_with_check(IN p_student_id integer, IN p_subject_id integer)
    language plpgsql
as
$$
BEGIN
    -- Видаляємо зв'язок студента з дисципліною і його оцінки там
    DELETE FROM student_subject WHERE student_id = p_student_id AND subject_id = p_subject_id;
    DELETE FROM task_result WHERE student_id = p_student_id AND task_id IN (SELECT task_id FROM task WHERE subject_id = p_subject_id);

    -- Перевіряємо, чи є інші підключення у студента до дисциплін
    IF NOT EXISTS (SELECT 1 FROM student_subject WHERE student_id = p_student_id) THEN
        -- Видаляємо студента, якщо інших підключень немає
        DELETE FROM task_result WHERE student_id = p_student_id;
        DELETE FROM student WHERE student_id = p_student_id;
    END IF;
END;
$$;

alter procedure delete_student_with_check(integer, integer) owner to postgres;

create function get_student_scores_by_subject()
    returns TABLE(result_id integer, task_id integer, student_id integer, score double precision)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT
            t.subject_id,
            0 AS placeholder,
            r.student_id,
            SUM(r.score) AS sum_score
        FROM
            task_result r
                JOIN
            task t ON r.task_id = t.task_id
        GROUP BY
            t.subject_id, r.student_id
        ORDER BY
            t.subject_id, r.student_id;
END;
$$;

alter function get_student_scores_by_subject() owner to postgres;

create function get_average_scores_by_task()
    returns TABLE(result_id integer, task_id integer, student_id integer, score numeric)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT
            t.subject_id,
            t.task_id,
            0 AS placeholder,
            CAST(AVG(tr.score) AS NUMERIC(10, 2)) AS average_score
        FROM
            task_result tr
                JOIN
            task t ON tr.task_id = t.task_id
        GROUP BY
            t.subject_id, t.task_id
        ORDER BY
            t.subject_id, t.task_id;
END;
$$;

alter function get_average_scores_by_task() owner to postgres;