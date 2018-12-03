VACUUM events;
BEGIN;
UPDATE events SET id = id WHERE id = 1;
UPDATE events SET id = id WHERE id = 2;
UPDATE events SET id = id WHERE id = 3;
COMMIT;

SELECT zdb.count('idxevents', dsl.or(terms('id', 1), terms('id', 2), terms('id', 3)));
VACUUM events;
SELECT zdb.count('idxevents', dsl.or(terms('id', 1), terms('id', 2), terms('id', 3)));
