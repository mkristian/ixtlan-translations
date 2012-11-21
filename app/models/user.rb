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

  def self.get_or_create( params = {} )
    a = first( :id => params['id'], :fields => [:id, :login, :name] )
    unless a
      a = create( params.merge( {:updated_at => DateTime.new( 0 ) }) )
    end
    a
  end

  def allowed_applications
    @_apps ||= groups.select { |g| g.application }.uniq
  end
end
