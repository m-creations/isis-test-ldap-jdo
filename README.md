isis-test-ldap-jdo
==================
This is a sample project to use DataNucleus LDAP datastores supports(using the datanucleus-ldap plugin).

This application is for test and development purposes only.

Project create with archetype of maven as follows:
```
mvn archetype:generate \
  -D archetypeGroupId=org.apache.isis.archetype \
  -D archetypeArtifactId=simpleapp-archetype \
  -D archetypeVersion=1.12.1 \
  -D groupId=com.mcreations.isis.ldap \
  -D artifactId=isis-test-ldap-jdo \
  -D version=1.0-SNAPSHOT \
  -B
```
Following modification has been done:
1. Launch files fixed to run inside Eclipse.
2. SimpleObject related files removed
3. Packages renamed
4. Role and Person domain models and their service repositories added

This project is based on Fusiondirectory LDAP schemas and you can use
one of LDAP dockers of m-creations to test:
```
docker run --name tmp-ldap-server -h ldapsrv.weave.local -p 10389:389 -e LDAP_DOMAIN=example.com -e LDAP_ORGANIZATION=com -d mcreations/fusiondirectory-ldap
```

After ruinning IsisTestLdapJdoApp-PROTOTYPE from run menu in eclipse, then the application is accessible from here:
http://localhost:8080/wicket