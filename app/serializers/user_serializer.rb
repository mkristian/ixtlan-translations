require 'ixtlan/babel/serializer'
class UserSerializer < Ixtlan::Babel::Serializer

  add_context(:single,
              :only => [:id],
              :include=> { 
                :groups => {
                  :only => [:name],
                  :include => {
                    :locales => { 
                      :only => [:id, :code]
                    },
                    :domains => { 
                      :only => [:id, :name]
                    },
                    :application => {
                      :only => [:id, :name]
                    }                      
                  }
                }
              })

end
