require "minitest_helper"

describe TranslationKeysController do

  before do
    @controller = TranslationKeysController.new
    @request = ActionController::TestRequest.new
    @response = ActionController::TestResponse.new
    @routes = Rails.application.routes

    key = TranslationKey.first_or_create( :name => 'key new', :application => Application.first )

    if key.new?
      key.state = :new
      key.updated_at = DateTime.now
      key.save
    end
  end

  after do
    TranslationKey.all( :name.like => 'extra%').destroy!
    TranslationKey.all( :name => 'key new').update( :state => :new )  
  end

  describe "access control" do

    it "wrong authentication" do
      request.env['X-SERVICE-TOKEN'] = 'something'
      lambda { put :update }.must_raise RuntimeError
    end

    it "not allowed" do
      lambda { put :commit }.must_raise RuntimeError
    end

    it "success" do
      request.env['X-SERVICE-TOKEN'] = 'be happy'
      put :rollback, :format => :json
    end

  end

  describe 'success' do

    before do
      unless RemotePermission.first(:allowed_ip => '0.0.0.0')
        RemotePermission.create(:allowed_ip => '0.0.0.0', 
                                :authentication_token => 'be happy',
                                :modified_by => User.first,
                                :application => Application.first)
      end
      request.env['X-SERVICE-TOKEN'] = 'be happy'
      
      TranslationKey.all( :name.like => 'extra%').destroy!
    end

    it 'update' do
      put :update, :translation_keys => ['key1','extra1','extra2'], :format => :json
    end

    it 'commit' do
      put :commit, :format => :json
    end

    it 'rollback' do
      put :rollback, :format => :json
    end

    after do
      body = JSON.parse(response.body)
      # puts body.to_yaml
      body.each do |item|
        translation = item["translation_key"]
        translation.size.must_equal 3
        translation['id'].wont_be_nil
        translation['name'].wont_be_nil
        translation['updated_at'].wont_be_nil
      end
    end

    it 'should deliver all committed' do
      TranslationKey.all(:name.like => "key_").update(:state => :ok)
      TranslationKey.all(:name => "new key").update(:state => :new)

      get :committed_last_changes, :format => :json, :updated_at => "2000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      #puts body.to_yaml
      body.size.must_equal 1
    end

    it 'should deliver all uncommitted' do
      TranslationKey.all(:name.like => "key_").update(:state => :ok)
      TranslationKey.all(:name => "new key").update(:state => :new)

      get :uncommitted_last_changes, :format => :json, :updated_at => "2000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      #puts body.to_yaml
      body.size.must_equal 2
    end

    it "should deliver nothing committed" do
      get :committed_last_changes, :format => :json, :updated_at => "3000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      body.size.must_equal 0
    end

    it "should deliver nothing uncommitted" do
      get :uncommitted_last_changes, :format => :json, :updated_at => "3000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      body.size.must_equal 0
    end
  end
end
