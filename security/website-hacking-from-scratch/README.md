# Website Hacking from Scratch

Here the [Course link](https://stackskills.com/courses/learn-website-hacking-from-scratch).

## Setting the lab

Will use Virtual Box to set the following machines: 

1. [Virtual Box image ready](https://www.offensive-security.com/kali-linux-vmware-virtualbox-image-download/) or [Official Kaly Image](https://www.kali.org/downloads/).
2. [Metaploitable](https://information.rapid7.com/download-metasploitable-2017.html).

## Usuarios
```bash
kali: root / toor
metaexploitable: msfadmin / msfadmin
```

## Create same NAT
1. Virtualbox > Archivo > Preferences > Red > pulsar boton + para NATNetwork
2. Que todos las maquinas conecten a NATNetwork > Machine > Config > Network > Conectar a NatNetwork

## Fix metaexplotable db issue
```
sudo nano /var/www/mutillidae/config.inc
$dbname=`owasp10`.
```

## Check connection

1. Init Metaexploitable and run `ifconfig`.
2. Start Kali and enter the IP returned by Metaexploitable `ifconfig`. Should find Metaexploitable websites.


## Ways to hack a Website

1. A través de una aplicación --> Web application pentesting
2. Computer uses an OS + Other aplications --> server side attacks
3. Managed by humans --> client side attacks

## Gathering info

1. http://whois.domaintools.com/
2. http://toolbar.netcraft.com/site_report?url=
3. https://www.exploit-db.com/
4. https://www.robtex.com/ (comprehensive DNS info)
5. https://github.com/guelfoweb/knock (discovering subdomains --> discovering user special part of the site)

### Files & directories discovery
Go to Kali
```bash
# ver docu
man dir
# access to metaexploitable dir
dirb http://ipdekali
dirb http://10.0.2.4/mutillidae/ (pillar url donde se guarda)
dirb http://10.0.2.4/mutillidae/ /usr/share/dirb/wordlists/common.txt
```

### Kali Maltego
Maltego is a great information gathering tool that can be used to gather information just about anything (people, websites, computers, servers ...etc). 
