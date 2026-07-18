SELECT '========================================' as Log;
SELECT 'Creating database and tables...' as Log;
SOURCE src/main/resources/sql_table_creation.sql;
SELECT 'Database and tables created successfully.' as Log;
SELECT '========================================' as Log;
SELECT 'Inserting document data into tables...' as Log;
SOURCE src/main/resources/document_data.sql;
SELECT 'Data inserted successfully.' as Log;
SELECT '========================================' as Log;