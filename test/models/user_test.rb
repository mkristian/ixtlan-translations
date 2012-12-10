require "minitest_helper"

describe User do

  subject do
    u = User.new({ :name => 'user',
                   :login => 'login'})
    u.groups = [Group.new({ 'name' => 'translator',
                            'locales' => [{ 'id' => 1 },
                                          { 'id' => 2 }],
                            'application' => { 'id' => 1 }
                          })]
    u
  end

  it "must exist" do
    subject.wont_be_nil
  end

  it 'must have groups with locales + application' do
    subject.groups.size.must_equal 1
    g = subject.groups[0]
    g.locales.must_equal Locale.all(:limit => 2)
    g.application.must_equal Application.first
  end

  it 'must calculate the allowed application with locales' do
    subject.allowed_applications.size.must_equal 1
    a = subject.allowed_applications[0]
    a.locales.must_equal Locale.all(:limit => 2)
    a.must_equal Application.first
  end
end
