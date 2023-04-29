import mysql.connector

connection = mysql.connector.connect(host='127.0.0.1',
                                     user='root',
                                     password='123456789',
                                     database='asm')

if connection.is_connected():
    db_Info = connection.get_server_info()
    print("Connected to MySQL Server version ", db_Info)
    cursor = connection.cursor()
    cursor.execute("select database();")
    record = cursor.fetchone()
    print("You're connected to database: ", record)
