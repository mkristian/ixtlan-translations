class Locale

  include DataMapper::Resource

  property :id, Serial, :auto_validation => false
  
  property :code, String, :required => true, :unique => true, :length => 5
  property :updated_at, DateTime, :required => true, :lazy => true

  # do not record timestamps since they are set from outside
  def set_timestamps_on_save
  end

end
