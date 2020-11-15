package sample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpPackage {
    private Date date;
    private byte[] data;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    public UdpPackage(byte[] data) {
        this.data = data;
        this.setDate(new Date(System.currentTimeMillis()));
    }

    public String getFormattedDate() {
        return formatter.format(date);
    }

    public String getDataAsString() {
        return new String(data);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
