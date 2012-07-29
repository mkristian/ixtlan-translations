require 'dm-rails/middleware/identity_map'
class ApplicationController < ActionController::Base

  respond_to :json

  rescue_from Ixtlan::Optimistic::ObjectStaleException do
    head :conflict
  end

  rescue_from DataMapper::ObjectNotFoundError do
    head :not_found
  end

  use Rails::DataMapper::Middleware::IdentityMap

  protected

  def serializer(resource)
    if resource
      @_factory ||= Ixtlan::Babel::Factory.new
      @_factory.new(resource)
    end
  end
end
