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
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.identity.StringIdentity;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;


@PersistenceCapable(identityType = IdentityType.APPLICATION, objectIdClass = StringIdentity.class, table = "ou=roles,dc=example,dc=com", schema = "organizationalRole")
@Queries({
    @Query(name = "findByName", language = "JDOQL", value = "SELECT FROM com.mcreations.isis.jdo.ldap.dom.security.Role WHERE name.indexOf(:name) >= 0 "),
    @Query(name = "findByPerson", language = "JDOQL", value = "SELECT FROM com.mcreations.isis.jdo.ldap.dom.security.Role r WHERE  "
            + " r.members.contains(:person) ")
})
@DomainObject(nature=Nature.EXTERNAL_ENTITY,autoCompleteRepository=Roles.class,autoCompleteAction="findByName")
public class Role  
{
    @Programmatic
    @Title
    public String theTitle(){
        return name;
    }
    
    @Property
    @PrimaryKey()
    @Column(name = "cn",allowsNull="false")
    private String name;


    public String getName() {
        return this.name;
//        return (name!=null && name.startsWith("cn=") ? name : "cn="  + name + ",ou=people,dc=example,dc=com");
    }

    public void setName(String name) {
        this.name = name;
//        this.name = (name!=null && name.startsWith("cn=") ? name : "cn="  + name + ",ou=people,dc=example,dc=com");
    }
    
    @Column(name = "roleOcuppant")
    @Extension(vendorName = "datanucleus", key = "attribute", value = "roleOccupant")
    /*@Join(column="uid")*/
    @Element(column="uid")
    @CollectionLayout(defaultView="table",hidden=Where.ALL_TABLES)
    protected Set<Person> members = new HashSet<Person>();

    
//    public Set<Person> getPersons() {
////        return persons;
//        return new HashSet<Person>(userRepository.findByRole(this));
//    }
//
//    public void setPersons(Set<Person> persons) {
//        this.persons = persons;
//    }

    public Set<Person> getMembers() {
        return new HashSet<Person>(personRepository.findByRole(this));
//        return roleOccupant;
    }
    
    
    public void setMembers(Set<Person> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Role other = (Role) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    @Inject
    Persons personRepository;
}
