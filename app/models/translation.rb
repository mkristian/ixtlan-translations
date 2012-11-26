class Translation

  include DataMapper::Resource

  belongs_to :translation_key, :key => true
  belongs_to :locale, :key => true
  belongs_to :domain, :key => true, :required => false

  property :text, String

  timestamps :at

  belongs_to :modified_by, 'User'

  def app_id
    translation_key.application.id
  end
end
