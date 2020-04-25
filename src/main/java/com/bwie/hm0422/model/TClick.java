package com.bwie.hm0422.model;

public class TClick {
    private String bId;

    private Short traffic;

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId == null ? null : bId.trim();
    }

    public Short getTraffic() {
        return traffic;
    }

    public void setTraffic(Short traffic) {
        this.traffic = traffic;
    }
}