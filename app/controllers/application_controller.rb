require 'dm-rails/middleware/identity_map'
class ApplicationController < ActionController::Base

  respond_to :json

  rescue_from Ixtlan::Optimistic::ObjectStaleException do
    head :conflict
  end

  rescue_from ActiveRecord::RecordNotFound do
    head :not_found
  end

  use Rails::DataMapper::Middleware::IdentityMap
  protect_from_forgery

  protected

  before_filter :cleanup

  def updated_at(key = params[:controller])
    @_updated_at ||= (params[key] || {})[:updated_at]
  end

  def cleanup(model)
    # compensate the shortcoming of the incoming json/xml
    model ||= []
    model.delete :id
    model.delete :created_at 
    @_updated_at ||= model.delete :updated_at
  end

  private

  after_filter :csrf

  def csrf
    response.header['X-CSRF-Token'] = form_authenticity_token
  end

  protected

  def serializer(resource)
    if resource
      @_factory ||= Ixtlan::Babel::Factory.new
      @_factory.new(resource)
    end
  end
end
