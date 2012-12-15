/*
 * ixtlan_gettext - helper to use fast_gettext with datamapper/ixtlan
 * Copyright (C) 2012 Christian Meier
 *
 * This file is part of ixtlan_gettext.
 *
 * ixtlan_gettext is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * ixtlan_gettext is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with ixtlan_gettext.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.mkristian.ixtlan.translations.client.restservices;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import de.mkristian.gwt.rails.session.Authentication;
import de.mkristian.gwt.rails.session.Session;
import de.mkristian.ixtlan.translations.client.models.User;

@Path("/session")
public interface SessionRestService extends RestService {

    @POST
    void create(Authentication authentication, MethodCallback<Session<User>> callback);

    @DELETE
    void destroy(MethodCallback<Void> callback);

    @POST @Path("/reset_password")
    void resetPassword(Authentication authentication, MethodCallback<Void> methodCallback);

    @PUT @Path("/ping")
    void ping(MethodCallback<Void> callback);
  }