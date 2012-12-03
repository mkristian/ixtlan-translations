require 'ixtlan/user_management/domain_resource'
class Ixtlan::UserManagement::Domain

  # use the same table as Domain
  def self.storage_name(repo = :default)
    'domains'
  end
end

class Domain < Ixtlan::UserManagement::Domain
end
