require "minitest_helper"

describe TranslationsController do

  before do
    @controller = TranslationsController.new
    @request = ActionController::TestRequest.new
    @response = ActionController::TestResponse.new
    @routes = Rails.application.routes
  end

  subject { @controller }

  describe "access control" do

    it "wrong authentication" do
      request.env['X-SERVICE-TOKEN'] = 'something'
      lambda { get :committed_last_changes }.must_raise RuntimeError
    end


    it "not allowed" do
      lambda { get :committed_last_changes }.must_raise RuntimeError
    end

  end

  describe "success" do

    before do      
      unless RemotePermission.first(:allowed_ip => '0.0.0.0')
        RemotePermission.create(:allowed_ip => '0.0.0.0', 
                                :authentication_token => 'be happy',
                                :modified_by => User.first,
                                :application => Application.first)
      end
      request.env['X-SERVICE-TOKEN'] = 'be happy'
      
      Translation.all(:text.like => 'hello%').destroy!
    end

    it 'should deliver all committed' do
      get :committed_last_changes, :format => :json, :updated_at => "2000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      # puts body.to_yaml
      body.size.must_equal 1
      body.each do |item|
        translation = item["translation"]
        translation.size.must_equal 5
        translation['locale_id'].wont_be_nil
        translation['domain_id'].must_be_nil
        translation['translation_key_id'].wont_be_nil
        translation['updated_at'].wont_be_nil
        translation['text'].wont_be_nil
      end
    end

    it "should deliver nothing" do
      get :committed_last_changes, :format => :json, :updated_at => "3000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      body.size.must_equal 0
    end
  end
end
