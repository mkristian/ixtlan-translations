require 'ixtlan/babel/serializer'
class SessionSerializer < Ixtlan::Babel::Serializer

  add_context(:single,
              :root => 'session',
              :only => [:permissions, :idle_session_timeout],
              :include=> { 
                :user => {
                  :include => [:applications]
                },
                :permissions => {
                  :include => [:actions, :associations]
                }
              }
           )

  default_context_key :single
end
