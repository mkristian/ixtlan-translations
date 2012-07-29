require "minitest_helper"

describe TranslationsController do

  #before { puts "Asd" }

  before do
    @controller = TranslationsController.new
    @request = ActionController::TestRequest.new
    @response = ActionController::TestResponse.new
    @routes = Rails.application.routes
  end

  subject { @controller }

  describe "last_changes" do
   it "respond with success" do
     get :last_changes
#      must_respond_with :success
    end
  end
end
