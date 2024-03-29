package nl.hsleiden.investore.data.tools;

import java.text.DecimalFormat;

public class ItemDataToStringTool {

    // Outputs given double in string with 2 decimals.
    public String doubleToString(double input) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(input);
    }

    public String doubleInCurrency(double input, String currency) {
        return currency + doubleToString(input);
    }

    public String doubleInPercentage(double input) {
        return doubleToString(input) + "%";
    }
}
