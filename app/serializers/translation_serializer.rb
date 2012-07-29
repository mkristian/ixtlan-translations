require 'ixtlan/babel/serializer'

class TranslationSerializer < Ixtlan::Babel::Serializer

  model Translation

  add_context(:single,
              :root => 'translation',
              :except => [:modified_by_id],
              :include => {
                :modified_by => {
                  :only => [:id, :login, :name]
                }
              }
             )

  add_context(:collection,
              :root => 'translation',
              :except => [:created_at, :modified_by_id]
             )

  default_context_key :single
end
