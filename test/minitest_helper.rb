ENV["RAILS_ENV"] = "test"
require File.expand_path('../../config/environment', __FILE__)
require 'rails/test_help'

require "minitest/autorun"

# HACK to get the controller test done
class MiniTest::Unit::TestCase
  include ::ActiveSupport::Testing::SetupAndTeardown
  include ::ActionController::TestCase::Behavior
end

require "minitest/rails"

# Uncomment if you want Capybara in accceptance/integration tests
# require "minitest/rails/capybara"

# Uncomment if you want awesome colorful output
# require "minitest/pride"

class MiniTest::Rails::ActiveSupport::TestCase
  # Setup all fixtures in test/fixtures/*.(yml|csv) for all tests in alphabetical order.
#  fixtures :all

  # Add more helper methods to be used by all tests here...
end

# Do you want all existing Rails tests to use MiniTest::Rails?
# Comment out the following and either:
# A) Change the require on the existing tests to `require "minitest_helper"`
# B) Require this file's code in test_helper.rb

MiniTest::Rails.override_testunit!

# create and seed test db
begin
  DataMapper.auto_upgrade!
rescue
  DataMapper.auto_migrate!
end
load File.expand_path('../../db/seeds.rb', __FILE__)

require 'factories'

Factory(:application) if Application.count == 1
if TranslationKey.count == 0
  # save the object from the factory - datamapper-bug ?
  Factory(:translation_key, :application => Application.first).tap{|t| t.save}
  Factory(:translation_key, :application => Application.last).tap{|t| t.save}

  Factory(:translation, :translation_key => TranslationKey.first).tap{|t| t.save}
  Factory(:translation, :translation_key => TranslationKey.last).tap{|t| t.save}
end
