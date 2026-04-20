DROP TABLE IF EXISTS t_transaction_details;

CREATE TABLE t_transaction_details (
    transaction_id BIGINT PRIMARY KEY,
    customer_id VARCHAR(20),
    transaction_date DATE,
    transaction_amount DOUBLE
);

INSERT INTO t_transaction_details VALUES (1,'C1','2026-04-01',120);
INSERT INTO t_transaction_details VALUES (2,'C1','2026-04-03',75);
INSERT INTO t_transaction_details VALUES (3,'C1','2026-04-05',130);
INSERT INTO t_transaction_details VALUES (4,'C1','2026-04-08',95);
INSERT INTO t_transaction_details VALUES (5,'C1','2026-04-10',40);

INSERT INTO t_transaction_details VALUES (6,'C1','2026-03-02',110);
INSERT INTO t_transaction_details VALUES (7,'C1','2026-03-07',65);
INSERT INTO t_transaction_details VALUES (8,'C1','2026-03-12',150);
INSERT INTO t_transaction_details VALUES (9,'C1','2026-03-15',85);
INSERT INTO t_transaction_details VALUES (10,'C1','2026-03-20',55);

INSERT INTO t_transaction_details VALUES (11,'C1','2026-02-02',120);
INSERT INTO t_transaction_details VALUES (12,'C1','2026-02-05',75);
INSERT INTO t_transaction_details VALUES (13,'C1','2026-02-10',60);
INSERT INTO t_transaction_details VALUES (14,'C1','2026-02-15',200);
INSERT INTO t_transaction_details VALUES (15,'C1','2026-02-20',90);

INSERT INTO t_transaction_details VALUES (16,'C2','2026-04-01',210);
INSERT INTO t_transaction_details VALUES (17,'C2','2026-04-03',80);
INSERT INTO t_transaction_details VALUES (18,'C2','2026-04-05',140);
INSERT INTO t_transaction_details VALUES (19,'C2','2026-04-07',65);
INSERT INTO t_transaction_details VALUES (20,'C2','2026-04-10',45);

INSERT INTO t_transaction_details VALUES (21,'C2','2026-03-01',120);
INSERT INTO t_transaction_details VALUES (22,'C2','2026-03-05',100);
INSERT INTO t_transaction_details VALUES (23,'C2','2026-03-08',170);
INSERT INTO t_transaction_details VALUES (24,'C2','2026-03-12',60);
INSERT INTO t_transaction_details VALUES (25,'C2','2026-03-15',75);

INSERT INTO t_transaction_details VALUES (26,'C2','2026-02-01',90);
INSERT INTO t_transaction_details VALUES (27,'C2','2026-02-05',130);
INSERT INTO t_transaction_details VALUES (28,'C2','2026-02-10',200);
INSERT INTO t_transaction_details VALUES (29,'C2','2026-02-15',55);
INSERT INTO t_transaction_details VALUES (30,'C2','2026-02-18',70);

INSERT INTO t_transaction_details VALUES (31,'C3','2026-04-01',50);
INSERT INTO t_transaction_details VALUES (32,'C3','2026-04-05',60);
INSERT INTO t_transaction_details VALUES (33,'C3','2026-04-07',75);
INSERT INTO t_transaction_details VALUES (34,'C3','2026-04-10',140);
INSERT INTO t_transaction_details VALUES (35,'C3','2026-04-12',110);

INSERT INTO t_transaction_details VALUES (36,'C3','2026-03-02',95);
INSERT INTO t_transaction_details VALUES (37,'C3','2026-03-05',180);
INSERT INTO t_transaction_details VALUES (38,'C3','2026-03-09',40);
INSERT INTO t_transaction_details VALUES (39,'C3','2026-03-12',120);
INSERT INTO t_transaction_details VALUES (40,'C3','2026-03-15',85);

INSERT INTO t_transaction_details VALUES (41,'C3','2026-02-02',70);
INSERT INTO t_transaction_details VALUES (42,'C3','2026-02-05',130);
INSERT INTO t_transaction_details VALUES (43,'C3','2026-02-08',200);
INSERT INTO t_transaction_details VALUES (44,'C3','2026-02-12',90);
INSERT INTO t_transaction_details VALUES (45,'C3','2026-02-16',60);

INSERT INTO t_transaction_details VALUES (46,'C4','2026-04-01',220);
INSERT INTO t_transaction_details VALUES (47,'C4','2026-04-03',75);
INSERT INTO t_transaction_details VALUES (48,'C4','2026-03-07',95);
INSERT INTO t_transaction_details VALUES (49,'C4','2026-02-10',110);
INSERT INTO t_transaction_details VALUES (50,'C4','2026-02-15',180);