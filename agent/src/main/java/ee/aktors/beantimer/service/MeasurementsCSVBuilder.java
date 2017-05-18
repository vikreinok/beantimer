package ee.aktors.beantimer.service;

import java.nio.charset.Charset;

/**
 *
 */
public class MeasurementsCSVBuilder {

    private static final int INIT_SIZE = 50000;

    public static final char DEFAULT_colSeparator = ';';
    public static final char EOL = '\n';
    public static final String CHARSET_NAME = "UTF-8";

    private StringBuilder data = new StringBuilder(INIT_SIZE);
    private char colSeparator;
    private int lastColumnInd = -1;


    public MeasurementsCSVBuilder() {
        this(DEFAULT_colSeparator);
    }

    public MeasurementsCSVBuilder(char colSeparator) {
        this.colSeparator = colSeparator;
    }

    /**
     * Add columns to CSV file
     *
     * @param columns
     */
    public MeasurementsCSVBuilder addColumns(Object... columns) {
        for (Object o : columns) addColumn(o);
        return this;
    }

    public MeasurementsCSVBuilder addEmptyColumns(int n) {
        for (int i = 0; i < n; i++) addColumn("");
        return this;
    }

    public MeasurementsCSVBuilder addColumn(Object column) {
        Object o = column;
        if (o == null) o = "";
        if (lastColumnInd > -1) data.append(colSeparator);
        data.append(o);
        ++lastColumnInd;
        return this;
    }

    public MeasurementsCSVBuilder nextRow() {
        data.append(EOL);
        lastColumnInd = -1;
        return this;
    }

    public byte[] getData() {
        return data.toString().getBytes(Charset.forName(CHARSET_NAME));
    }
}