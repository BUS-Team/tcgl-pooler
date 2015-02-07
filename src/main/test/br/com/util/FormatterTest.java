package br.com.util;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.junit.Assert;
import org.junit.Test;

public class FormatterTest {

    @Test
    public void testToDaysHoursMinutes() throws Exception {
        PeriodFormatter p = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" day", " days")
                .appendSeparator(" and ")
                .appendMinutes()
                .appendSuffix(" minute", " minutes")
                .appendSeparator(" and ")
                .appendSeconds()
                .appendSuffix(" second", " seconds")
                .toFormatter();

        Assert.assertEquals(PeriodFormatter.class, Formatter.toDaysHoursMinutes().getClass());
    }
}