# intialize application
Dir[ File.join( File.expand_path( 'translations', 
                                  File.dirname( __FILE__ ) ), 
                "*.rb" ) ].sort.each do |f|
  warn "[Translations] Init 'translations/#{File.basename(f)}"
  require f
end

CubaAPI.accept :json, :yaml

CubaAPI.define do
  on 'bla' do
    res.write 'bla'
  end

  on 'audits' do #, allowed?( 'guest', 'root' ) do
    run Ixtlan::Audit::Cuba
  end
  on 'errors' do #, allowed?( 'root' ) do
    run Ixtlan::Errors::Cuba
  end
  
  on default do
    run Translations::Application
  end
end
