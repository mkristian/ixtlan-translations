class LocalController < ApplicationController

  protect_from_forgery

  protected

  before_filter :cleanup

  def updated_at(key = params[:controller])
    @_updated_at ||= (params[key] || {})[:updated_at]
  end

  def cleanup(model = nil)
    #TODO cleanup nested hashes
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

  def current_user(user = nil)  
    if user
      session['user'] = serializer(user).to_hash
      @_current_user = user
    else
      @_current_user ||= begin
                           data = session['user']
                           if data
                             data = data.dup
                             u = User.get!(data['id'])
                             u.groups = data['groups'].collect { |g| Group.new( g.dup ) }
                             u
                           end
                         end
    end
  end
end
