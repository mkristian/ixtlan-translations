class Translation

  include DataMapper::Resource

  belongs_to :translation_key, :key => true
  belongs_to :locale, :key => true

  property :text, String

  timestamps :at

  belongs_to :modified_by, 'User'

end
