package org.dhamma.translations.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import org.dhamma.translations.client.models.TranslationKey;

@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface TranslationKeysRestService extends RestService {

  @GET @Path("/translation_keys")
  void index(MethodCallback<List<TranslationKey>> callback);

  @GET @Path("/translation_keys/{id}")
  void show(@PathParam("id") int id, MethodCallback<TranslationKey> callback);

}
