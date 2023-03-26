import os

AMASS_PATH = 'C:\\Users\\PC\\go\\bin\\amass.exe'

domain = input("Enter domain:")
output_file = "subdomains.txt"

cmd = os.system(f"{AMASS_PATH} enum -d {domain} -o {output_file}")

with open(output_file, "r") as f:
    subdomains = f.readlines()


with open("subdomains.txt", "r") as f:
    subdomains = f.read().splitlines()



