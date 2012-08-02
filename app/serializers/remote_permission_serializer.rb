require 'ixtlan/babel/serializer'

class RemotePermissionSerializer < Ixtlan::Babel::Serializer

  model RemotePermission

  add_context(:single,
              :root => 'remote_permission'
             )

  add_context(:collection,
              :root => 'remote_permission',
              :except => [:created_at]
             )

  default_context_key :single
end
