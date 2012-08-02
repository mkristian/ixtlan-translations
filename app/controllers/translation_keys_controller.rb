class TranslationKeysController < RemoteController

  public

  # PUT /translation_keys/commit
  def commit
    @transaction_keys = serializer(application.commit_keys).use(:remote)
    respond_with @transaction_keys
  end

  # PUT /translation_keys/rollback
  def rollback
    @transaction_keys = serializer(application.rollback_keys).use(:remote)
    respond_with @transaction_keys
  end

  # PUT /translation_keys
  def update
    @transaction_keys = serializer(application.update_keys(params[:translation_keys])).use(:remote)
    respond_with @transaction_keys
  end
end
