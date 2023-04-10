import os
import signal
import subprocess
import json
import DB_Connection

cursor = DB_Connection.cursor
HttpxPath = "C:\\Users\\PC\\go\\bin\\httpx.exe"
HttpxOutput = "D:\\Python201c\\PythonProject\\untitled\\httpx.txt"
cmd = os.system(f"{HttpxPath} -target 35.244.218.227 -ports 443 -status-code -no-color -tech-detect  -o output_httpx.txt")
with open("output_httpx.txt", "r") as f:
    output = f.read()
query8 = "SELECT domain.id FROM asm.domain where domain.domain_name LIKE '%tryhackme.com%'ORDER BY domain.id DESC LIMIT 1;"
cursor.execute(query8)
for i in cursor.fetchone():
    idMax = i
# print(idMax)
# query9 = "INSERT INTO `asm`.`result_httpx`(`output`,`id_domain`) VALUES ( '" + str(output) + "','" + str(idMax) + "');"
# cursor.execute(query9)
# DB_Connection.connection.commit()

list_web_tech = output.split(" ")
for i in list_web_tech[2].split(","):
    with open("Tech.txt", "w") as f:
        f.write(i)




