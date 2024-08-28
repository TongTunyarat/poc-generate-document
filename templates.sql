BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "templates" (
	"id"	TEXT,
	"code"	TEXT,
	"name"	TEXT,
	"path"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "templates" ("id","code","name","path") VALUES ('pr001','PR','testPR','/templates/pr.docx'),
 ('po001','PO','testPO','/templates/po.docx'),
 ('or001','OR','testOR','/templates/or.docx');
COMMIT;
