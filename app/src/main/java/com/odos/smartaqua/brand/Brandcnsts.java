package com.odos.smartaqua.brand;

public class Brandcnsts {

    private int brandid;
    private String brandname;
    private String brandcode;
    private String cretaedby;

    public Brandcnsts(int brandid, String brandname, String brandcode, String cretaedby) {
        this.brandid = brandid;
        this.brandname = brandname;
        this.brandcode = brandcode;
        this.cretaedby = cretaedby;
    }

    public int getBrandid() {
        return brandid;
    }

    public String getBrandname() {
        return brandname;
    }

    public String getBrandcode() {
        return brandcode;
    }

    public String getCretaedby() {
        return cretaedby;
    }
}
