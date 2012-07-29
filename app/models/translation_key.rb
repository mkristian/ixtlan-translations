class TranslationKey

  include DataMapper::Resource

  property :id, Serial

  property :name, String, :required => true

  property :state, Enum[:ok, :new, :hidden, :deleted, :restored], :required => true

  timestamps :at

  belongs_to :application
end
