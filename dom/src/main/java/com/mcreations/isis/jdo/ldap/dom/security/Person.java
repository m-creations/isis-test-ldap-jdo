/**********************************************************************
Copyright (c) 2008 Stefan Seelmann and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


Contributors :
 ...
 ***********************************************************************/
package com.mcreations.isis.jdo.ldap.dom.security;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.identity.StringIdentity;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

@PersistenceCapable(identityType = IdentityType.APPLICATION, objectIdClass = StringIdentity.class,table="ou=people,dc=example,dc=com",schema="top,person,organizationalPerson,inetOrgPerson,gosaAccount")
@Queries({
    @Query(name = "findByName", language = "JDOQL", value = "SELECT FROM com.mcreations.isis.jdo.ldap.dom.security.Person WHERE fullName.indexOf(:name) >= 0 "),
    @Query(name = "findByRole", language = "JDOQL", value = "SELECT FROM com.mcreations.isis.jdo.ldap.dom.security.Person WHERE roles.contains(:role) ")
})
@DomainObject( nature=Nature.EXTERNAL_ENTITY,autoCompleteRepository=Persons.class,autoCompleteAction="findByName")
public class Person 
{
    @Programmatic
    @Title
    public String theTitle(){
        return fullName;
    }
    
    @PrimaryKey
    @Column(name = "uid",allowsNull="false")
    private String userId;
    
    @Column(name = "cn")
    private String fullName;
    
    @Column(name = "givenName")
    private String firstName;

    @Column(name = "sn")
    private String lastName;

    public String getUserId() {
        return this.userId;
//        return (userId!=null && userId.startsWith("uid=") ? userId : "uid="  + userId + ",ou=people,dc=example,dc=com");
    }

    public void setUserId(String userId) {        
//        this.userId = (userId!=null && userId.startsWith("uid=") ? userId : "uid="  + userId + ",ou=people,dc=example,dc=com");
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Persistent(mappedBy = "members")
//    @Element(column="roleOccupant")
//    @Join(column="uid")
//    @Element(column="roleOcuppant")
    @CollectionLayout(defaultView="table",hidden=Where.ALL_TABLES)
    protected Set<Role> roles= new HashSet<Role>();


    public Set<Role> getRoles() {
        return new HashSet<Role>(roleRepository.findByPerson(this));
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
   

    @Inject
    Roles roleRepository;
}
