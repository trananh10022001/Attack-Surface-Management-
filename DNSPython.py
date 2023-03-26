import dns.resolver
import json

with open("subdomains.txt", "r") as f:
    subdomains = f.read().splitlines()

result = ""
data = {}
for subdomain in subdomains:
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

with open('output.json', 'w') as f:
    json.dump(data, f)