/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.mcreations.isis.jdo.ldap.dom.security;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;


/**
 * 
 * @author Reza Rahimi <rahimi@mcreations.com>
 *
 */
@DomainService(repositoryFor = Person.class)
@DomainServiceLayout(menuOrder = "20")
public class Roles  {

	public TranslatableString title() {
		return TranslatableString.tr("Roles");
	}

	// region > listAll (action)
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "List All Roles", bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(name = "LDAP Security", sequence = "1")
	public List<Role> listAll() {
		return repositoryService.allInstances(Role.class);
	}
	// endregion
	
	// region > find (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Find Role by Name", bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(name = "LDAP Security", sequence = "3")
	public List<Role> findByName(@ParameterLayout(named = "name") final String name) {
        return repositoryService.allMatches(new QueryDefault<Role>(Role.class, "findByName","name",name));
    }
    // endregion
    
    // region > find (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Find Role by Person", bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(name = "LDAP Security", sequence = "3")
    public List<Role> findByPerson(@ParameterLayout(named = "Person") final Person person) {
        return repositoryService.allMatches(new QueryDefault<Role>(Role.class, "findByPerson","person",person));
    }
    // endregion
    
    // region > find (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "New Role", bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(name = "LDAP Security", sequence = "4")
    public Role newRole(@ParameterLayout(named = "Name") final String name) {
        Role obj = new Role();//= factoryService.instantiate(Role.class);
        PersistenceManager pm = isisJdoSupport.getJdoPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try
        {
//            tx.begin();
            obj.setName(name);
            obj.setMembers(new HashSet<>());
            pm.makePersistent(obj);
//            Object id = pm.getObjectId(obj);
            tx.commit();
//            pm.close();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
//            pm.close();
        }
//        obj.setName(name);
//        obj.setRoleOccupant(new HashSet<>());
//        repositoryService.persist(obj);
//        if(!repositoryService.isPersistent(obj))
//            new Exception("Reza Exception");
        return obj;
        
    }
    // endregion
    
	// region > injected services
	@Inject
	DomainObjectContainer container;
	@Inject
	RepositoryService repositoryService;
	@Inject
	FactoryService factoryService;
	@Inject
	IsisJdoSupport isisJdoSupport; 
	// endregion
}
