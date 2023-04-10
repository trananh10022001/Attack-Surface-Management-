import mysql.connector

connection = mysql.connector.connect(host='localhost',
                                     database='asm',
                                     user='root',
                                     password='1234')

if connection.is_connected():
    db_Info = connection.get_server_info()
    print("Connected to MySQL Server version ", db_Info)
    cursor = connection.cursor()
    cursor.execute("select database();")
    record = cursor.fetchone()
    print("You're connected to database: ", record)

