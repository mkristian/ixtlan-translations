require 'ixtlan/user_management/user_resource'
class Ixtlan::UserManagement::User

  # use the same table as User
  def self.storage_name(repo = :default)
    'users'
  end
end
class User < Ixtlan::UserManagement::User

  def self.get_or_create( params = {} )
    a = first( :id => params['id'], :fields => [:id, :login, :name] )
    unless a
      a = create( params.merge( {:updated_at => DateTime.new( 0 ) }) )
    end
    a
  end

  def allowed_applications
    @_apps ||= groups.collect { |g| g.application }.uniq
  end
end
