class DateTime
  def self.now
    n = super
    n.utc
  end

  def to_s(arg = nil)
    if arg
      super
    elsif utc?
      strftime('%Y-%m-%d %H:%M:%S.') + ("%06d" % (sec_fraction / Date::NANOSECONDS_IN_DAY / 1000))
    else
      utc.to_s
    end
  end

  # def parse(arg = nil)
  #   super "#{args}+0:00" 
  # end
end
