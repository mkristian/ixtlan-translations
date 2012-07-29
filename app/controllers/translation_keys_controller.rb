class TranslationsController < RemoteController

  public

  # PUT /translation_keys/commit
  def commit
    application.commit_keys
    head :ok
  end

  # PUT /translation_keys/rollback
  def rollback
    application.rollback_keys
    head :ok
  end

  # PUT /translation_keys
  def update
    application.update_keys(params[:keys])
    head :ok
  end
end
