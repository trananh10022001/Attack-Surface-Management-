import os
import nmap3
import DB_Connection
from datetime import datetime
import dns.resolver

#AMASS
AMASS_PATH = 'C:\\Users\\PC\\go\\bin\\amass.exe'

#domain = input("Enter domain:")
domain = "tryhackme.com"
output_file = "subdomains.txt"
now = datetime.now()
cursor = DB_Connection.cursor

query0 = "SELECT COUNT(*) FROM asm.domain WHERE domain.domain_name = '"+str(domain)+"'"
query1 = "INSERT INTO `asm`.`domain`(`domain_name`,`date_created`) VALUES ( '"+str(domain)+"','"+str(now)+"');"
cursor.execute(query1)
cursor.execute(query0)
count = 0
for i in cursor.fetchone():
    count = i
DB_Connection.connection.commit()

#cmd = os.system(f"{AMASS_PATH} enum -d {domain} -o {output_file}")

with open(output_file, "r") as f:
    subdomains = f.readlines()

for subdomain in subdomains:
    query2 =""
    if count > 0:
        query2 = "SELECT domain.id FROM asm.domain where domain.domain_name LIKE '%" + domain + "%'ORDER BY domain.id DESC LIMIT 1;"
    data1 = cursor.execute(query2)
    for i in cursor.fetchone():
        id_domain = i
    query3 ="INSERT INTO `asm`.`subdomains_amass`(`subdomain_name`,`date_created`,`id_domain`) VALUES ( '"+str(subdomain.strip())+"','"+str(now)+"','"+str(id_domain)+"');"
    cursor.execute(query3)
DB_Connection.connection.commit()
#query1 = "SELECT * FROM asm.domain where domain_name = 'remote-us-west-1.tryhackme.com';"
#data = cursor.execute(query1)
#for i in cursor.fetchall():
 #   print(i)

#DNSPython
with open("subdomains.txt", "r") as f:
    subdomains1 = f.read().splitlines()
result = ""
data = {}
for subdomain in subdomains1:
    try:
        answers = dns.resolver.resolve(subdomain)
        for answer in answers:
            data[subdomain] = str(answer)
        for answer in answers:
            print(f"{subdomain}: {answer}")
            result += f"{subdomain}: {answer} \n"
    except dns.resolver.NXDOMAIN:
        print(f"{subdomain}: NXDOMAIN")

with open("listIP.txt", mode='w') as f:
    f.write(result)
with open("ListIP.txt", "r") as f:
    subdomains2 = f.readlines()

for i in subdomains2:
    list = i.strip().split(": ")
    query4 = "SELECT subdomains_amass.id FROM asm.subdomains_amass where subdomains_amass.subdomain_name = '"+list[0]+"'ORDER BY subdomains_amass.id DESC LIMIT 1;"
    data2 = cursor.execute(query4)
    for j in cursor.fetchone():
        id_subdomain = j
    query5 ="INSERT INTO `asm`.`ip_subdomains`(`ip`,`date_created`,`id_subdomain`) VALUES ( '"+str(list[1])+"','"+str(now)+"'"+",'"+str(id_subdomain)+"');"
    cursor.execute(query5)
DB_Connection.connection.commit()

query6 = "SELECT * FROM asm.ip_subdomains;"
data3 = cursor.execute(query6)
for i in cursor.fetchall():
   print(i)

###NMAP
NUCLEI_PATH = "C:\\Users\\PC\\go\\bin\\nuclei.exe"
listIpFile = "listIP.txt"
nmap = nmap3.Nmap()


with open(listIpFile, "r") as f:
    listIP = f.read().splitlines()

list = []
for i in listIP:
    list.append(i.split(": ")[1].strip())
for ip in list:
    idIpMax = 0
    query8 = "SELECT ip_subdomains.id FROM asm.ip_subdomains where ip_subdomains.ip = '" +str(ip)+"' ORDER BY ip_subdomains.id DESC LIMIT 1;"
    cursor.execute(query8)
    for j in cursor.fetchone():
        idIpMax = j
    print(idIpMax)
    results = nmap.scan_top_ports(ip.strip())
    ports = []
    for port in results[ip.strip()]['ports']:
        port_id = port['portid']
        state = port['state']
        service = port['service']['name']
        ports.append({'port': port_id, 'state': state, 'service': service})
        with open("nmap.txt", "w") as file:
            for port in ports:
                query9 = "INSERT INTO `asm`.`result_nmap`(`port_number`,`status`,`protocol`,`id_ip`) VALUES ( '"+str(port['port'])+"','"+str(port['state'])+"'"+",'"+str(port['service'])+"','"+str(idIpMax)+"');"
                cursor.execute(query9)
                file.write(f"Port: {port['port']}, State: {port['state']}, Service: {port['service']}\n")
        DB_Connection.connection.commit()
        for port in ports:
            for i in ports:
                if port['port'] == "80" and i['port'] == "443" and port['state'] == 'open':
                    print(f"Port 80 and 443 are open in {ip}. Scanning for vulnerabilities with Nuclei and Httpx...")
                    print('nuclei result:')
                    cmd = os.system(f"{NUCLEI_PATH} -target {ip} -o nuclei.txt")
                    with open("nuclei.txt", "r") as f:
                        outputNuclei = f.read()
                    idMax = 0
                    query8 = "SELECT ip_subdomains.id FROM asm.ip_subdomains where ip_subdomains.ip = '" + str(ip.strip()) + "' ORDER BY ip_subdomains.id DESC LIMIT 1;"
                    cursor.execute(query8)
                    for i in cursor.fetchone():
                        idMax = i
                    outputNuclei = outputNuclei.replace("'", "")
                    query9 = "INSERT INTO `asm`.`result_nuclei`(`output`,`id_ip`) VALUES ( '" + str(outputNuclei) + "','" + str(idMax) + "');"
                    cursor.execute(query9)
                    DB_Connection.connection.commit()
                elif port['port'] == "80" and i['port'] == "443" and port['state'] == 'closed':
                    print(f"Port 80 and 443 are closed. Scanning for vulnerabilities with Nmap...")
                    results = nmap.nmap_version_detection(ip,args="--script vulners --script-args mincvss+1.0 -o output_Vuls_Nmap.txt")
                    with open("output_Vuls_Nmap.txt", "r") as f:
                        outputVulsNmap = f.read()
                    idMax = 0
                    query10 = "SELECT ip_subdomains.id FROM asm.ip_subdomains where ip_subdomains.ip = '" + str(ip.strip()) + "' ORDER BY ip_subdomains.id DESC LIMIT 1;"
                    cursor.execute(query10)
                    for i in cursor.fetchone():
                        idMax = i
                    query11 = "INSERT INTO `asm`.`result_vuls_nmap`(`output`,`id_ip`) VALUES ( '" + str(outputVulsNmap) + "','" + str(idMax) + "');"
                    cursor.execute(query11)
                    DB_Connection.connection.commit()
