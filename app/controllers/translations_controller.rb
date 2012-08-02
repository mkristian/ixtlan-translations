class TranslationsController < RemoteController

  public

  # GET /translations/last_changes
  def last_changes
    @translations = serializer(application.translations_all(nil, params[:updated_at])).use(:collection)
    respond_with @translations
  end

end
