class RemoteLocale < ActiveResource::Base
  self.site = Translations::Application.config.remote_service_url
  self.headers['X-SERVICE-TOKEN'] = Translations::Application.config.remote_service_token

  self.element_name = "locale"
end
