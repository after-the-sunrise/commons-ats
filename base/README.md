# commons-ats-base
[![Build Status][travis-icon]][travis-page] [![Coverage Status][coverall-icon]][coverall-page] [![Maven Central][maven-icon]][maven-page]

Boilerplate code libraries to support the standard Java libraries.

## Examples

### JVM Process ID
```java
        Runtimes.getProcessId()); // Java 9 : java.lang.Process#pid​()
```

### Listener Management
```java
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                // Do something 1
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                // Do something 2
            }
        };

        PredicateListenerManager<Runnable> manager = new ArrayListenerManagerImpl<Runnable>();
        manager.addListener(r1);
        manager.addListener(r2);

        manager.process(new ListenerPredicate<Runnable>() {
            @Override
            public void process(Runnable listener) {
                listener.run();
            }
        });
        
        manager.removeListener(r2);
        manager.removeListener(r1);
```

### Holiday Adjustment
```java
        TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(tz);

        // Weekend
        Collection<DayType> weekends = Arrays.asList(DayType.SATURDAY, DayType.SUNDAY);

        // Public holidays
        Collection<Long> holidays = new ArrayList<Long>();
        holidays.add(df.parse("2012-04-30").getTime()); // Monday
        holidays.add(df.parse("2012-05-01").getTime()); // Tuesday

        // Date to adjust (Saturday)
        Date date = df.parse("2012-04-28");

        // Following : "2012-05-02"
        Long following = AdjustmentType.FOLLOWING.getNext(date.getTime(), tz, weekends, holidays);
        System.out.println(df.format(new Date(following)));

        // Preceding : "2012-04-27"
        Long preceding = AdjustmentType.PRECEDING.getNext(date.getTime(), tz, weekends, holidays);
        System.out.println(df.format(new Date(preceding)));

        // Modified Following : "2017-05-*" -> "2017-04-27"
        Long modifiedFollowing = AdjustmentType.MODIFIED_FOLLOWING.getNext(date.getTime(), tz, weekends, holidays);
        System.out.println(df.format(new Date(modifiedFollowing)));

        // Modified Preceding : "2017-04-27"
        Long modifiedPreceding = AdjustmentType.MODIFIED_PRECEDING.getNext(date.getTime(), tz, weekends, holidays);
        System.out.println(df.format(new Date(modifiedPreceding)));
```

[travis-page]:https://travis-ci.org/after-the-sunrise/commons-ats
[travis-icon]:https://travis-ci.org/after-the-sunrise/commons-ats.svg?branch=master
[coverall-page]:https://coveralls.io/github/after-the-sunrise/commons-ats?branch=master
[coverall-icon]:https://coveralls.io/repos/github/after-the-sunrise/commons-ats/badge.svg?branch=master
[maven-page]:https://maven-badges.herokuapp.com/maven-central/com.after_sunrise.commons/commons-ats-base
[maven-icon]:https://maven-badges.herokuapp.com/maven-central/com.after_sunrise.commons/commons-ats-base/badge.svg
