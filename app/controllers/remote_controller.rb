require 'ixtlan/remote/access_controller'
class RemoteController < ApplicationController

  skip_before_filter :authorize
  before_filter :remote_permission

  private

  include Ixtlan::Remote::AccessController

  protected

  def application
    @appliation ||= RemotePermission.get!(remote_permission.id).application
  end
end
