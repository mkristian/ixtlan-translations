class TranslationKey

  include DataMapper::Resource

  property :id, Serial

  property :name, String

  timestamps :at
end
