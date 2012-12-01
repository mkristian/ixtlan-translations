package de.mkristian.ixtlan.translations.client.models;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Translation implements HasToDisplay, Identifyable {

  public final int id;

  @Json(name = "updated_at")
  private Date updatedAt;

  @Json(name = "modified_by")
  private User modifiedBy;

  private String text;

  @Json(name = "translation_key_id")
  private final int translationKeyId;

  @Json(name = "locale_id")
  private final int localeId;
  
  @Json(name = "domain_id")
  private final int domainId;

  @Json(name = "app_id")
  private int appId;

  transient private TranslationKey key;

  transient private String defaultText;

  transient private String originalText;
  
  @JsonCreator
  public Translation(@JsonProperty("translationKeyId") int translationKeyId, 
          @JsonProperty("localeId") int localeId, 
          @JsonProperty("domainId") int domainId, 
          @JsonProperty("appId") int appId, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy){
      // just to fulfil the identifyable interface
      this.id = translationKeyId << 20 + domainId << 10 + localeId;
      this.translationKeyId = translationKeyId;
      this.localeId = localeId;
      this.domainId = domainId;
      this.appId = appId;
      this.updatedAt = updatedAt;
      this.modifiedBy = modifiedBy;
  }

  public int getId(){
    return id;
  }

  public int getTranslationKeyId() {
    return translationKeyId;
  }

  public int getLocaleId() {
    return localeId;
  }
  
  public String getLocaleCode() {
      return key == null ? null : key.application().detectLocale(localeId).getCode();
    }

  public String getDefaultLocaleCode() {
      return key == null ? null : key.application().getDefaultLocale().getCode();
    }

  public int getDomainId() {
    return domainId;
  }

  public String getDomainName() {
      return key == null ? null : key.application().detectDomain(domainId).getName();
  }
  
  public Date getUpdatedAt(){
    return updatedAt;
  }

  public User getModifiedBy(){
    return modifiedBy;
  }

  public String getText(){
    return text;
  }

  public void setText(String value){
    text = value == null ? "" : value;
  }
  
  public int getAppId(){
      return appId;
  }

  public String getKey(){
      return key.getName();
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Translation) && 
        ((Translation)other).id == id;
  }

  public String toDisplay() {
    return text;
  }

  public void update(Translation trans) {
      this.text = trans.text;
      this.updatedAt = trans.getUpdatedAt();
      this.modifiedBy = trans.getModifiedBy();
  }

  public TranslationKey getTranslationKey() {
      return this.key;
  }

  public void setTranslationKey(TranslationKey key) {
      if (key.id != translationKeyId){
          throw new IllegalArgumentException("mismatch ids: key.id=" + key.id + " translationKeyId=" + translationKeyId );
      }
      this.key = key;
  }

  public void setOriginalText(Translation translation) {
      this.originalText = translation.getText();
  }

  public String getOriginalText() {
      return originalText;
  }

  public void setDefaultText(Translation translation) {
      this.defaultText = translation.getText();
  }
  
  public String getDefaultText() {
      return defaultText;
  }
}
