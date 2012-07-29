class Translation

  include DataMapper::Resource

  property :id, Serial

  property :text, String

  timestamps :at

  belongs_to :modified_by, 'User'
  belongs_to :locale
  belongs_to :translation_key

end
