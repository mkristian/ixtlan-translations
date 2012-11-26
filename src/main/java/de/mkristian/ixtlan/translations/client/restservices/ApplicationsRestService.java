package de.mkristian.ixtlan.translations.client.restservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Translation;

@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface ApplicationsRestService extends RestService {

  @GET @Path("/applications")
  void index(MethodCallback<List<Application>> callback);

  @GET @Path("/applications/{id}")
  void show(@PathParam("id") int id, MethodCallback<Application> callback);

  @PUT @Path("/applications/{id}/translations")
  void save(@PathParam("id") @Attribute("getAppId()") Translation translation, 
          MethodCallback<Translation> callback);

}