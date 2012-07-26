class Authentication < ActiveResource::Base

  #if Translations::Application.config.respond_to :remote_service_url
  self.site = Translations::Application.config.remote_service_url
  self.headers['X-SERVICE-TOKEN'] = Translations::Application.config.remote_service_token

  def self.reset_password(login)
    begin
      post(:reset_password, :login => login)
      true
    rescue ActiveResource::ResourceNotFound
      false
    end
  end

end
