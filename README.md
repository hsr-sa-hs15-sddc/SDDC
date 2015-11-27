# SDDC [![Build Status](https://magnum.travis-ci.com/silvanadrian/SDDC.svg?token=oDB79Cz1oFYjcyRt63K2&branch=master)](https://magnum.travis-ci.com/silvanadrian/SDDC)


# SonarQube [![SonarQube Coverage](https://img.shields.io/sonar/http/sonar.silvn.com/SDDC:SDDC/coverage.svg)](http://sonar.silvn.com/dashboard/index/1) [![SonarQube Tech Debt](https://img.shields.io/sonar/http/sonar.silvn.com/SDDC:SDDC/tech_debt.svg)](http://sonar.silvn.com/dashboard/index/1)
CodeCoverage und generelle Metriken können hier angesehen werden:
<a href="http://sonar.silvn.com">http://sonar.silvn.com</a>

#REST API + Dashboard

Server startet auf port 8080 (API Paths -> /api/services, /api/orderedservices, /api/servicemodules)

##Customer Dashboard

<a href="http://app.silvn.com/#/services">http://app.silvn.com/#/services</a>

##Admin Dashboard

<a href="http://app.silvn.com/#/admin/services">http://app.silvn.com/#/admin/services</a>

#Dockerfile

```
docker pull silvanadrian/sddc
```

##Docker Container ausführen

```
docker run -d -p 80:8080 silvanadrian/sddc
```
