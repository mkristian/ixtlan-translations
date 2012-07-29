class TranslationsController < RemoteController

  public

  # PUT /translations/last_changes
  def last_changes
    @translations = serialzer(application.translations_all(nil, params[:updated_at]))

    respond_with @translations
  end

end
