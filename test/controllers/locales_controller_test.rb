require "minitest_helper"

describe LocalesController do

  before do
    @controller = LocalesController.new
    @request = ActionController::TestRequest.new
    @response = ActionController::TestResponse.new
    @routes = Rails.application.routes
  end

  subject { @controller }

  describe "access control" do

    it "wrong authentication" do
      request.env['X-SERVICE-TOKEN'] = 'something'
      lambda { get :last_changes }.must_raise RuntimeError
    end


    it "not allowed" do
      lambda { get :last_changes }.must_raise RuntimeError
    end

  end

  describe "success" do

    before do      
      unless RemotePermission.first(:ip => '0.0.0.0')
        RemotePermission.create(:ip => '0.0.0.0', 
                                :token => 'be happy',
                                :modified_by => User.first,
                                :application => Application.first)
      end
      request.env['X-SERVICE-TOKEN'] = 'be happy'
    end

    it 'should deliver all committed' do
      get :last_changes, :format => :json, :updated_at => "2000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      #puts body.to_yaml
      body.size.must_equal 2
      body.each do |item|
        translation = item["locale"]
        translation.size.must_equal 3
        translation['id'].wont_be_nil
        translation['code'].wont_be_nil
        translation['updated_at'].wont_be_nil
      end
    end

    it "should deliver nothing" do
      get :last_changes, :format => :json, :updated_at => "3000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      body.size.must_equal 0
    end
  end
end
