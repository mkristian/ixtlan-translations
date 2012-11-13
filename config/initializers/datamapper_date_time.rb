SECONDS_IN_DAY = 60 * 60 * 24
NANOSECONDS_IN_DAY = SECONDS_IN_DAY * 1000 * 1000 * 1000
class DateTime
  def self.now
    n = super
    n.utc
  end

  def to_s(arg = nil)
    if arg && ! arg.is_a?(Symbol)
      super
    elsif utc?
      strftime('%Y-%m-%dT%H:%M:%S.') + ("%06d" % (sec_fraction / NANOSECONDS_IN_DAY / 1000))
    else
      utc.to_s
    end
  end

#  def self.parse(str)
#    result = super (str+"+0:00").sub(/ /, 'T')
#    result
#  end
end
