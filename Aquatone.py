import nmap3
import socket
import json
import os

# Khởi tạo đối tượng nmap
nmap = nmap3.Nmap()

# Đường dẫn nuclei
AQUATONE_PATH = 'C:\\Ruby32-x64\\bin\\aquatone.exe'

# Địa chỉ IP cần quét
while True:
    ip = input("Enter IP address to scan: ").strip()
    try:
        socket.inet_aton(ip)
        break
    except socket.error:
        print("Invalid IP address, please try again.")
        continue

# Quét các cổng được sử dụng trên địa chỉ IP đã cho
results = nmap.scan_top_ports(ip)

# Hiển thị thông tin các cổng
ports = []
for port in results[ip]['ports']:
    port_id = port['portid']
    state = port['state']
    service = port['service']['name']
    if service == 'http':
        service += ' (HTTP)'
    elif service == 'https':
        service += ' (HTTPS)'
    ports.append({'port': port_id, 'state': state, 'service': service})

# Hiển thị kết quả
print(f"Các cổng mở trên địa chỉ IP {ip} là:")
for port in ports:
    print(f"Port {port['port']}: {port['state']}, {port['service']}")

print('')
for port in ports:

    for i in ports:
            if port['port'] == "80" and i['port'] == "443" and port['state'] == 'open':
                print(f"Port 80 and 443 are open. Scanning for vulnerabilities with Aquatone...")
                cmd = os.system(f"{AQUATONE_PATH} aquatone-scan -p -chrome-path {ip}")

            elif port['port'] == "80" and i['port'] == "443" and port['state'] == 'closed':
                print(f"Port 80 and 443 are closed. Scanning for vulnerabilities with Nmap...")
                results = nmap.nmap_version_detection(ip, args='-sV --script vulners')
                print(results)
                vulnerabilities = []
                try:
                    data = json.loads(results)
                    for host in data.get("host"):
                        for port in host.get("ports").get("port"):
                            for script in port.get("script"):
                                if script.get("id") == "vulners":
                                    vulns = script.get("elem")
                                    for vuln in vulns:
                                        vuln_info = {
                                            "name": vuln.get("name"),
                                            "cvss": vuln.get("cvss"),
                                            "url": vuln.get("refs")[0].get("url")
                                        }
                                vulnerabilities.append(vuln_info)
                except:
                    print("Error parsing Nmap results.")
                    if len(vulnerabilities) > 0:
                        print("The following vulnerabilities were found:")
                        for vuln in vulnerabilities:
                            print(f"- {vuln['name']} (CVSS: {vuln['cvss']}) - {vuln['url']}")
                    else:
                         print("No vulnerabilities were found.")