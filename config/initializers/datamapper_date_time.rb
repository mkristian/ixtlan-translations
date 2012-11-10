SECONDS_IN_DAY = 60 * 60 * 24
NANOSECONDS_IN_DAY = SECONDS_IN_DAY * 1000 * 1000 * 1000
class DateTime
  def self.now
    n = super
    n.utc
  end

  def to_s(arg = nil)
    if arg
      super
    elsif utc?
      strftime('%Y-%m-%d %H:%M:%S.') + ("%06d" % (sec_fraction / NANOSECONDS_IN_DAY / 1000))
    else
      utc.to_s
    end
  end
end