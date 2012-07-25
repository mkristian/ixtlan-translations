class User
  include ActiveModel::Serializers::JSON
  include ActiveModel::Serializers::Xml
  
  attr_accessor :login, :name, :groups

  def attributes
    {'login' => login, 'name' => name, 'groups' => groups.collect { |g| g.attributes } }
  end

  def initialize(attributes = {})
    @login = attributes['login']
    @name = attributes['name']
    @groups = (attributes['groups'] || []).collect {|g| Group.new g }
  end

  def self.authenticate(login, password)
    result = User.new
    if password.blank?
      result.log = "no password given with login: #{login}"
    elsif login.blank?
      result.log = "no login given"
    elsif password == "behappy"
      result.login = login
      result.name = login.humanize
      result.groups = [Group.new('name' => login)]
    else
      result.log = "wrong password for login: #{login}"
    end
    result
  end

  def self.reset_password(login)
    result = User.new(:login => login)
    begin
      Authentication.post(:reset_password, :login => login)
    rescue ActiveResource::ResourceNotFound
      result.log = "User(#{login}) not found"
    end
    result
  end

  def log=(msg)
    @log = msg
  end

  def to_log
    if @log
      @log
    else
      "User(#{id ? (id.to_s + ':') : ''}#{login})"
    end
  end

  def valid?(ignore)
    @log.nil?
  end

  def new_record?
    false
  end
  alias :destroyed? :new_record?

end
