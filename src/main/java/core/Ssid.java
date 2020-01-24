package core;

import java.util.Date;

public class Ssid {
    public Ssid(Integer ssid, Date date) {
        this.ssid = ssid;
        this.date = date;
    }

    private Integer ssid;

    private Date date;

    public Integer getSsid() {
        return ssid;
    }

    public void setSsid(Integer ssid) {
        this.ssid = ssid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
