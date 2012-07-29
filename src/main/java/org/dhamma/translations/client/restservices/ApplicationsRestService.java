package org.dhamma.translations.client.restservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.dhamma.translations.client.models.Application;
import org.dhamma.translations.client.models.Translation;
import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;

@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface ApplicationsRestService extends RestService {

  @GET @Path("/applications")
  void index(MethodCallback<List<Application>> callback);

  @GET @Path("/applications/{id}")
  void show(@PathParam("id") int id, MethodCallback<Application> callback);

  @POST @Path("/application/{id}/translations")
  void createTranslation(@PathParam("id") @Attribute("applicationId") Translation translation, 
          MethodCallback<Translation> callback);

  @PUT @Path("/application/{id}/translations")
  void updateTranslation(@PathParam("id") @Attribute("applicationId") Translation translation, 
          MethodCallback<Translation> callback);

  @GET @Path("/applications/{id}/translations/{locale}")
  void translations(@PathParam("id") int id, 
          @PathParam("locale") String locale, 
          MethodCallback<List<Translation>> callback);
}
