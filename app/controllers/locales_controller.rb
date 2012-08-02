class LocalesController < RemoteController

  public

  # GET /locales/last_changes
  def last_changes
    @locales = serializer(Locale.changed_all(params[:updated_at])).use(:collection)
    respond_with @locales
  end

end
