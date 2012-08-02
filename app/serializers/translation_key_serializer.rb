require 'ixtlan/babel/serializer'

class TranslationKeySerializer < Ixtlan::Babel::Serializer

  model TranslationKey

  add_context(:single,
              :root => 'translation_key',
              :only => [:id, :name, :state]
             )

  add_context(:remote,
              :root => 'translation_key',
              :only => [:id, :name, :updated_at]
             )

  add_context(:collection,
              :root => 'translation_key',
              :except => [:created_at]
             )

  default_context_key :single
end
