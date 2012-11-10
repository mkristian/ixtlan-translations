require 'ixtlan/user-management/application_resource'
require 'ixtlan/user-management/user_resource'

rest = Translations::Application.config.restserver

rest.server( :users ) do |server|
  server.url = "http://localhost:3000"
  server.options[:headers] = {'X-Service-Token' => 'be happy'}
  server.add_model( Ixtlan::UserManagement::User, :users )
  server.add_model( Ixtlan::UserManagement::Application, :applications ) 
end
