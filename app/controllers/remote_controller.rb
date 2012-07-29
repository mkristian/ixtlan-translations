class RemoteController < ApplicationController

  skip_before_filter :authorize

  private

  def x_service_token
    request.headers['X-SERVICE-TOKEN']
  end

  def remote_permission
    perm = RemotePermission.find_by_token(x_service_token)
    raise "ip #{request.remote_ip} wrong authentication" unless perm 
    # if the perm.id == nil then do not check IP 
    # server clusters have many IPs
    raise "ip #{request.remote_ip} not allowed" if (!perm.ip.blank? && request.remote_ip != perm.ip)
    perm
  end

  protected

  def application
    @appliation ||= remote_permission.application
  end
end
