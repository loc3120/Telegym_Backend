IF NOT EXISTS
    (
        SELECT 1
        from Role
        WHERE id = '11111111-1111-1111-1111-111111111111'
           OR id = '22222222-2222-2222-2222-222222222222'
    )
    INSERT INTO Role
    VALUES ('11111111-1111-1111-1111-111111111111', 'ROLE_ADMIN', '19700101',
            '88888888-8888-8888-8888-888888888888', '19700101', '88888888-8888-8888-8888-888888888888'),
           ('22222222-2222-2222-2222-222222222222', 'ROLE_STAFF', '19700101',
            '88888888-8888-8888-8888-888888888888', '19700101', '88888888-8888-8888-8888-888888888888');

IF NOT EXISTS
    (
        SELECT 1
        from TypeExercise
        WHERE id = '33333333-3333-3333-3333-333333333333'
           OR id = '44444444-4444-4444-4444-444444444444'
    )
    INSERT INTO TypeExercise
    VALUES ('33333333-3333-3333-3333-333333333333', 'GYM', '19700101',
            '88888888-8888-8888-8888-888888888888', '19700101', '88888888-8888-8888-8888-888888888888'),
           ('44444444-4444-4444-4444-444444444444', 'YOGA', '19700101',
            '88888888-8888-8888-8888-888888888888', '19700101', '88888888-8888-8888-8888-888888888888');