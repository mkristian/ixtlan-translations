require 'ixtlan/user_management/user_resource'
class Ixtlan::UserManagement::User

  # use the same table as Locale
  def self.storage_name(repo = :default)
    'users'
  end
end
class User < Ixtlan::UserManagement::User

  # include DataMapper::Resource

  # property :id, Serial, :auto_validation => false
  
  # property :login, String, :required => true, :unique => true, :length => 32
  # property :name, String, :required => true, :length => 128
  # property :updated_at, DateTime, :required => true

  # attr_accessor :groups, :applications

  # # do not record timestamps since they are set from outside
  # def set_timestamps_on_save
  # end

  def allowed_translate_applications
    @_apps ||= 
      begin
        apps = []
        groups.select do |g|
          a = g.application_of_translator
          apps << a if a
        end
        apps
      end
  end
end
