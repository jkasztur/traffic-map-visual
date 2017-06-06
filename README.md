# traffic-map-visual
Visualization of traffic accidents, weather information and statistics on map background

This project is part of PB138 course in MUNI FI

## Building
To build this project run
~~~
mvn clean install
~~~

## Deployment into containers
#### Apache Tomcat / Jboss Web Server
##### Preparations
Before deploying make sure you have configured Tomcat server.
There should be at least one user/password in your `$TOMCAT_DIR/conf/tomcat-users.xml`
If there is not, you should add:
`<user username="tomcat" password="tomcat" roles="tomcat,manager-script"/>`


Then looks into your maven settings.xml file. If there is no tomcat server configured, add:
```
<settings>
...
	<servers>
		...
		<server>
			<id>tomcat-server</id>
			<username>tomcat</username>
			<password>tomcat</password>
		</server>
	</servers>
	...
</settings>
```
##### Deployment
Run in project directory:
`
mvn tomcat7:deploy
`

##### Access
You can access the webapp at `http://localhost:8080/traffic-map-visual`
##### Undeployment

Run in project directory:
`
mvn tomcat7:undeploy
`

#### Apache Karaf / Jboss Fuse
##### Preparations
Make sure there is at least one user in $KARAF_HOME/etc/users.properties
If there is not, add
for Karaf:
```
karaf = karaf,_g_:admingroup
_g_\:admingroup = group,admin,manager,viewer,systembundles
 ```
for Fuse:
```
admin=admin,admin,manager,viewer,Monitor, Operator, Maintainer, Deployer, Auditor, Administrator, SuperUser
```
##### Deployment
There are options `-Pkaraf` for Apache Karaf and `-Pfuse` for Jboss Fuse
To deploy the project to Karaf run in project directory:
```
mvn install -Pkaraf
```

##### Access
You can access the webapp at `http://localhost:8080/traffic-map-visual`

##### Undeployment
Run in project directory:
```
mvn clean -Pkaraf
```
## Authors:

- Róbert Ďuriančík  
- Matej Sojak  
- Jan Kasztura

## Supervisor:
- RNDr. Adam Rambousek, Ph.D.
