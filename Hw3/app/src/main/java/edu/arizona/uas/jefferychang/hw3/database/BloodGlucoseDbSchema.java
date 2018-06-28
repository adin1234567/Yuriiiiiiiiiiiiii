package edu.arizona.uas.jefferychang.hw3.database;

public class BloodGlucoseDbSchema {
    public static final class BloodGlucoseTable {
        public static final String NAME = "bloodglucose";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String BREAKFAST = "breakfast";
            public static final String LUNCH = "lunch";
            public static final String DINNER = "dinner";
            public static final String FASTING = "fasting";
            public static final String DATE = "date";
            public static final String NORMAL = "normal";
        }
    }
}
