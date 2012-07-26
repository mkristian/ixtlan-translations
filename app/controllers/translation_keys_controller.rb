class TranslationKeysController < ApplicationController

  public

  # GET /translation_keys
  def index
    @translation_keys = serializer(TranslationKey.all).use(:collection)
    respond_with @translation_keys
  end

  # GET /translation_keys/1
  def show
    @translation_key = serializer(TranslationKey.get!(params[:id]))
    respond_with @translation_key
  end
end
