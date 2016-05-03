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

import java.util.List;

import javax.inject.Inject;

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
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;


/**
 * 
 * @author Reza Rahimi <rahimi@mcreations.com>
 *
 */
@DomainService(repositoryFor = Role.class)
@DomainServiceLayout(menuOrder = "20")
public class Persons  {

	public TranslatableString title() {
		return TranslatableString.tr("Persons");
	}

	// region > listAll (action)
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "List All Persons", bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(name = "LDAP Security", sequence = "2")
	public List<Person> listAll() {
		return repositoryService.allInstances(Person.class);
	}
	// endregion
    // region > find (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Find Person by Name", bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(name = "LDAP Security", sequence = "3")
    public List<Person> findByName(@ParameterLayout(named = "name") final String name) {
        return repositoryService.allMatches(new QueryDefault<Person>(Person.class, "findByName","name",name));
    }
    // endregion
    
    // region > find (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Find Person by Role", bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(name = "LDAP Security", sequence = "3")
    public List<Person> findByRole(@ParameterLayout(named = "Role") final Role role) {
        return repositoryService.allMatches(new QueryDefault<Person>(Person.class, "findByRole","role",role));
    }
    // endregion
	// region > injected services
	@Inject
	DomainObjectContainer container;
	@Inject
    RepositoryService repositoryService;
	// endregion
}
