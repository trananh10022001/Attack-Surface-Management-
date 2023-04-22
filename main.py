import os
import nmap3
import DB_Connection
from datetime import datetime
import dns.resolver
import re
import requests
from bs4 import BeautifulSoup

def scanDomain(domain):
    #AMASS
    AMASS_PATH = 'C:\\Users\\DOAN\\go\\bin\\amass.exe'
    output_file = "subdomains.txt"
    cmd = os.system(f"{AMASS_PATH} enum -passive -d {domain} -o {output_file}")
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
            print(f"{subdomain}: no such domain")

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

    ###NMAP
    NUCLEI_PATH = "C:\\Users\\DOAN\\go\\bin\\nuclei.exe"
    listIpFile = "listIP.txt"
    nmap = nmap3.Nmap()


    with open(listIpFile, "r") as f:
        listIP = f.read().splitlines()

    list = []
    for i in listIP:
        list.append(i.split(": ")[1].strip())
    for ip in list:
        print(ip)
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
                    file.write(f"Port: {port['port']}, State: {port['state']}, Service: {port['service']}\n")
            
        for port in ports:
            query9 = "INSERT INTO `asm`.`result_nmap`(`port_number`,`status`,`protocol`,`id_ip`) VALUES ( '"+str(port['port'])+"','"+str(port['state'])+"'"+",'"+str(port['service'])+"','"+str(idIpMax)+"');"
            cursor.execute(query9)
        DB_Connection.connection.commit()

        for port in ports:
            if (port['port'] == "80" or port['port'] == "443") and port['state'] == 'open':
                print(f"Port "+ str(port['port']) +" are open in "+str(ip)+". Scanning for vulnerabilities with Nuclei and Httpx...")
                print('nuclei result:')
                cmd = os.system(f"{NUCLEI_PATH} -target "+str(ip)+":"+port['port'] +" -o nuclei.txt")
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
                
                #httpx
                DB_Connection.connection.commit()
                cursor = DB_Connection.cursor

                HttpxPath = "C:\\Users\\DOAN\\go\\bin\\httpx.exe"
                cmd = os.system(f"{HttpxPath} -target {ip} -ports "+port['port'] +" -no-color -tech-detect  -o output_httpx.txt")
                with open("output_httpx.txt", "r") as f:
                    output = f.read()
                query8 = "SELECT domain.id FROM asm.domain where domain.domain_name LIKE '%"+str(domain)+"%'ORDER BY domain.id DESC LIMIT 1;"
                cursor.execute(query8)
                for i in cursor.fetchone():
                    idMax = i
                
                query9 = "INSERT INTO `asm`.`result_httpx`(`output`,`id_domain`) VALUES ( '" + str(output) + "','" + str(idMax) + "');"
                cursor.execute(query9)
                DB_Connection.connection.commit()
                dict_web_tech = {}
                matches = re.findall(r'\[(.*?)\]', output)
                for i in matches:
                    list_web_tech = str(i).split(",")
                    for j in list_web_tech:
                        if j not in dict_web_tech:
                            dict_web_tech[j] = 1
                        else:
                            dict_web_tech[j] += 1
                for tech in dict_web_tech:
                    url = f'https://vulmon.com/searchpage?q={tech}&sortby=byrelevance'
                    response = requests.get(url)
                    if(response.status_code == 200):
                        soup = BeautifulSoup(response.content, 'html.parser')
                        page_num = soup.find_all('div', class_='ui pagination menu')
                        if not page_num:
                            num = 2
                        else:
                            for pages in page_num:
                                soup = BeautifulSoup(pages.text, 'html.parser')
                                pages = soup.extract('a')
                                a = str(pages).replace("NEXT »",'').replace(' ', '')
                                pages = len(a)
                                num = pages - 2


                    for count in range(1, num):
                        url = f'https://vulmon.com/searchpage?q={tech}&sortby=byrelevance&page={count}'
                        response = requests.get(url)
                        if (response.status_code == 200):
                            soup = BeautifulSoup(response.content, 'html.parser')
                            text = ""
                            text1 = soup.find_all('a', class_= 'header')
                            list1=[]
                            for i in text1 :
                                if(str(i).__contains__("header item") == False):
                                    soup2 = BeautifulSoup(i.text,features='html.parser')
                                    title = soup2.extract('a')
                                    list1.append(title)
                                    print(list1)



                            list2 = []
                            text2 = soup.find_all('div',class_='value')
                            for i in text2:
                                soup3 = BeautifulSoup(i.text, 'html.parser')
                                cvss_point = soup3.extract('div')
                                list2.append(cvss_point)
                                print(list2)


                            list3 = []
                            text3 = soup.find_all('div',class_='description')
                            for i in text3:
                                soup3 = BeautifulSoup(i.text, 'html.parser')
                                Descript = soup3.extract('a')
                                list3.append(Descript)
                                print(list3)

                            list4=[]
                            text4 = soup.find_all('div',class_='extra')
                            for i in text4:
                                soup4 = BeautifulSoup(i.get_text('\n'), 'html.parser')
                                web_tech = soup4.extract('div')
                                list4.append(web_tech)
                                print(list4)

                            list5 =[]
                            for i in text1:
                                if (str(i).__contains__("header item") == False):
                                    cve = i.get('href')
                                    cve_link = 'https://vulmon.com'+cve.strip()
                                    list5.append(cve_link)
                                    print(list5)
                            cursor = DB_Connection.cursor
                            query20 = "SELECT domain.id FROM asm.domain where domain.domain_name LIKE '%" + domain + "%'ORDER BY domain.id DESC LIMIT 1;"
                            cursor.execute(query20)
                            for i in cursor.fetchone():
                                id_domain = i
                            for i in range(0,len(list1)):
                                query = "INSERT INTO `asm`.`cve`(`cve_id`,`cvss_point`,`descriptions`,`web_tech`,`link`,`id_domain`) VALUES ( '" +str(list1[i]) + "','" + str(list2[i]) + "','" + str(list3[i]).replace("&lt;=","before version").replace("'","") + "','" +str(list4[i])+ "','" +str(list5[i])+"','"+str(id_domain)+"');"
                                cursor.execute(query)
                            DB_Connection.connection.commit()
                        else:
                            break

        print(f"Scanning "+str(ip) + " for vulnerabilities with Nmap...")
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

def run_scan(domain):
     scanDomain(domain)

run_scan('asmsp23.online')

