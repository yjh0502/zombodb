CREATE TABLE issue272 (
  id   SERIAL8 NOT NULL PRIMARY KEY,
  data JSON
);

SELECT zdb_define_mapping('issue272', 'data', '{
  "type": "nested",
  "include_in_all": true,
  "properties": {
    "obj1": {
      "type": "nested",
      "properties": {
        "key1": {
          "type": "keyword",
          "ignore_above": 10921,
          "normalizer": "exact"
        },
        "key2": {
          "type": "keyword",
          "ignore_above": 10921,
          "normalizer": "exact"
        }
      }
    }
  }
}');

CREATE INDEX idxissue272
  ON issue272 USING zombodb (zdb('issue272', ctid), zdb(issue272)) WITH (url='localhost:9200/');

INSERT INTO issue272 (data) VALUES ('{
  "obj1": [
    {
      "key1": "val1",
      "key2": "val1"
    },
    {
      "key1": "val2",
      "key2": "val2"
    },
    {
      "key1": "val1",
      "key2": "val2"
    }
  ]
}');

INSERT INTO issue272 (data) VALUES ('{
  "obj1": [
    {
      "key1": "val10",
      "key2": "val10"
    },
    {
      "key1": "val20",
      "key2": "val20"
    },
    {
      "key1": "val10",
      "key2": "val20"
    }
  ]
}');

-- should return id=1
SELECT *
FROM issue272
WHERE zdb('issue272', ctid) ==> 'data.obj1.key1=val1 WITH data.obj1.key2=val1';

DROP TABLE issue272 CASCADE;