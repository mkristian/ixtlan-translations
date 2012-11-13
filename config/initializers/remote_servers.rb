require 'ixtlan/user_management/authentication_model'

rest = Translations::Application.config.rest

rest.server( :users ) do |server|
  server.url = "http://localhost:3000"
  server.options[:headers] = {'X-Service-Token' => 'be happy'}
  server.add_model( User )
  server.add_model( Application )
  server.add_model( Domain )
  server.add_model( Ixtlan::UserManagement::Authentication, :authentications )
end
