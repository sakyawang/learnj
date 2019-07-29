package lean.base.enums;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-5-7 下午4:38
 */
public enum DateRange {

    HOUR(1, new IDateRange() {
        @Override
        public long startTime() {
            return System.currentTimeMillis();
        }

        @Override
        public long endTime() {
            return System.currentTimeMillis() - ONE_HOUR_TIME_MILLIS;
        }
    }),
    DAY(2, new IDateRange() {
        @Override
        public long startTime() {
            return System.currentTimeMillis();
        }

        @Override
        public long endTime() {
            return System.currentTimeMillis() - ONE_DAY_TIME_MILLIS;
        }
    }),
    WEEK(3, new IDateRange() {
        @Override
        public long startTime() {
            return System.currentTimeMillis();
        }

        @Override
        public long endTime() {
            return System.currentTimeMillis() - ONE_WEEK_TIME_MILLIS;
        }
    });

    private static final long ONE_HOUR_TIME_MILLIS = 3600 * 1000;

    private static final long ONE_DAY_TIME_MILLIS = ONE_HOUR_TIME_MILLIS * 24;

    private static final long ONE_WEEK_TIME_MILLIS = ONE_DAY_TIME_MILLIS * 7;

    private int index;

    private IDateRange dateRange;

    DateRange(int index, IDateRange dateRange) {
        this.index = index;
        this.dateRange = dateRange;
    }

    public synchronized long startTime() {
        return this.dateRange.startTime();
    }

    public synchronized long endTime() {
        return this.dateRange.endTime();
    }
}
