require 'ixtlan/babel/serializer'

class TranslationKeySerializer < Ixtlan::Babel::Serializer

  model TranslationKey

  add_context(:single,
              :root => 'translation_key'
             )

  add_context(:collection,
              :root => 'translation_key',
              :except => [:created_at]
             )

  default_context_key :single
end
