require "minitest_helper"

describe Group do

  describe 'Translator' do
    subject do
      Group.new({ 'name' => 'translator',
                  'locales' => [{ 'id' => 1 }],
                  'application' => { 'id' => 1 }
                })
    end

    it "must exist" do
      subject.wont_be_nil
    end

    it 'must have locales + application' do
      subject.locales.must_equal Locale.all(:limit => 1)
      subject.application.must_equal Application.first
    end

    it 'must calculate the application of translator with locales' do
      a = subject.application
      a.locales.must_equal Locale.all(:limit => 1)
      a.must_equal Application.first
    end
  end

  describe 'Any' do
    subject do
      Group.new({ 'name' => 'any' })
    end

    it "must exist" do
      subject.wont_be_nil
    end

    it 'must have no locales + no application' do
      subject.locales.must_equal []
      subject.application.must_be_nil
    end

    it 'must calculate no application' do
      subject.application.must_be_nil
    end
  end

end
