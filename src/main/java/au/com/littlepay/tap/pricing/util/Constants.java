package au.com.littlepay.tap.pricing.util;

public class Constants {

    public static final int FIRST_STOP_INDEX = 0;
    public static final int SECOND_STOP_INDEX = 1;

    public static final int MAX_STOPS_PER_BUS_PER_PAN = 2;
    public static final String EMPTY_STRING = "";
    public static final String SEPARATOR = ",";
    public static final String COMPOSITE_KEY_SEPARATOR = ":";

    public static final String[] HEADER = {"Started", "Finished", "DurationSecs", "FromStopId",
            "ToSTopId", "ChargeAmount", "CompanyId", "BusId", "PAN", "Status"};

    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

}
