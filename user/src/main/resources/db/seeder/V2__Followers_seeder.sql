DO $$
DECLARE
    follower UUID;
    influencer UUID;
    i INT;
BEGIN
    FOR i IN 1..1000 LOOP
        -- Select a random follower
        SELECT id INTO follower
        FROM users
        ORDER BY RANDOM()
        LIMIT 1;

        SELECT id INTO influencer
        FROM users
        WHERE id != follower
        ORDER BY RANDOM()
        LIMIT 1;

        INSERT INTO followers (follower_id, influencer_id)
        VALUES (follower, influencer)
        ON CONFLICT DO NOTHING;
    END LOOP;
END $$;