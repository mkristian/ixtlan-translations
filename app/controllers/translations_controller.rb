class TranslationsController < RemoteController

  public

  # GET /translations/committed/last_changes
  def committed_last_changes
    @translations = application.translations_all( true, 
                                                  params[:updated_at] )
    respond_with serializer( @translations )
  end

  # GET /translations/uncommitted/last_changes
  def uncommitted_last_changes
    @translations = application.translations_all( false, 
                                                  params[:updated_at] )
    respond_with serializer( @translations )
  end

end
