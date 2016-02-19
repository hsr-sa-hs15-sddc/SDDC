# SDDC [![Build Status](https://travis-ci.org/hsr-sa-hs15-sddc/SDDC.svg?branch=master)](https://travis-ci.org/hsr-sa-hs15-sddc/SDDC)



#REST API + Dashboard

Server startet auf port 8080 (API Paths -> /api/services, /api/orderedservices, /api/servicemodules)

##Customer Dashboard

<a href="http://example.com/#/services">http://example.com/#/services</a>

##Admin Dashboard

<a href="http://example.com/#/admin/services">http://example.com/#/admin/services</a>

##REST API Doku

<a href="http://example.com/swagger-ui.html">http://example.com/swagger-ui.html</a>

#Dockerfile

```
docker pull silvanadrian/sddc
```

##Docker Container ausführen

```
docker run -d -p 80:8080 silvanadrian/sddc
```
