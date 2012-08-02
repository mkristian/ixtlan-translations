require 'ixtlan/remote/remote_access_controller'
class RemoteController < ApplicationController

  skip_before_filter :authorize

  private

  include Ixtlan::Remote::RemoteAccessController

  protected

  def application
    #p ::RemotePermission.get!(1)
    #::RemotePermission.first
    #p ::RemotePermission.get!(remote_permission.id)
    @appliation ||= RemotePermission.get!(remote_permission.id).application
  end
end
