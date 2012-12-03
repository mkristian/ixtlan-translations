package de.mkristian.ixtlan.translations.client.models;


import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS, name = "translation_key")
public class TranslationKey implements HasToDisplay, Identifyable {

  public final int id;

  private String name;

  public TranslationKey(){
    this(0);
  }
  
  @JsonCreator
  public TranslationKey(@JsonProperty("id") int id){
    this.id = id;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public void setName(String value){
    name = value;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof TranslationKey) && 
        ((TranslationKey)other).id == id;
  }

  public String toDisplay() {
    return name;
  }
  
  private final Map<String, Translation> translations = new HashMap<String, Translation>();

  transient private Application application;

  private String key( Locale local, Domain domain ) {
      return key( local == null ? 0 : local.id, domain.id );
    }

  private String key( int localId, int domainId ) {
      return localId + "|" + domainId;
  }

  public void addTranslation(Translation translation){
      this.translations.put(key(translation.getLocaleId(),
                                 translation.getDomainId() ), 
                             translation);
      translation.setTranslationKey(this);
  }

  public void setApplication( Application app ){
      this.application = app;
  }
  
  public Application application(){
      return this.application;
  }
  
  /**
   * retrieve the translation, cascade:
   * <li>look for locale + domain</li>
   * <li>look for locale + Domain.NONE</li>
   * <li>look for app.defaultLocale + domain</li>
   * <li>take the translation key as 'translation' (as gettext does)</li>
   * @param locale
   * @param domain
   * @return
   */
  public Translation findTranslation( Locale locale, Domain domain ){
      Translation result = doFindTranslation(locale, domain);
      if( domain != Domain.NONE ){
          result.setDefaultText( doFindTranslation( locale, Domain.NONE ) );
      }
      if( ! locale.equals( application.getDefaultLocale() ) ){
          result.setOriginalText( doFindTranslation( application.getDefaultLocale(), domain ) );
      }
      return result;
  }

  private Translation doFindTranslation( Locale locale, Domain domain ){
      Translation result = translations.get( key( locale, domain ) );
      if ( result == null ){
          String text = null;
          if ( domain != Domain.NONE ){
              result = translations.get(key( locale, Domain.NONE));
              if ( result != null ){
                  text = result.getText();
              }
          }
          if ( result == null ){
              text = getName();
          }
          result = new Translation( id, locale.getId(), domain.getId(), 
                  application.id, null, null );
          result.setText( text );
      }
      result.setTranslationKey(this);
      return result;
  }

}
