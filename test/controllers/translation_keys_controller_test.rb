require "minitest_helper"

describe TranslationKeysController do

  before do
    @controller = TranslationKeysController.new
    @request = ActionController::TestRequest.new
    @response = ActionController::TestResponse.new
    @routes = Rails.application.routes
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
      unless RemotePermission.first(:ip => '0.0.0.0')
        RemotePermission.create(:ip => '0.0.0.0', 
                                :token => 'be happy',
                                :modified_by => User.first,
                                :application => Application.first)
      end
      request.env['X-SERVICE-TOKEN'] = 'be happy'
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
      body.empty?.must_equal false
     # puts body.to_yaml
      body.each do |item|
        translation = item["translation_key"]
        translation.size.must_equal 3
        translation['id'].wont_be_nil
        translation['name'].wont_be_nil
        translation['updated_at'].wont_be_nil
      end
    end
  end
end
