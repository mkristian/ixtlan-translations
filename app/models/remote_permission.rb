require 'ixtlan/remote/permission'
class Ixtlan::Remote::Permission
  
  # use the same table as RemotePermission
  def self.storage_name(repo = :default)
    'remote_permissions'
  end
end

class RemotePermission < Ixtlan::Remote::Permission

  belongs_to :application, :unique => true

  timestamps :at

  belongs_to :modified_by, 'User'
end
