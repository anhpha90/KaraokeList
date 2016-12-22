package vn.com.pqs.model;

import java.io.Serializable;

/**
 * Created by long on 03/12/2016.
 */
public class BaiHat implements Serializable {
    private String txtms;
   private String tenBh;
    private String txtcs;
   private String txtLr;
   private Boolean thich;
    private int img;

    public BaiHat() {
    }

    public BaiHat(String txtms, String tenBh, String txtcs, String txtLr, Boolean thich, int img) {
        this.txtms = txtms;
        this.tenBh = tenBh;
        this.txtcs = txtcs;
        this.txtLr = txtLr;
        this.thich = thich;
        this.img = img;
    }

    public String getTxtms() {
        return txtms;
    }

    public void setTxtms(String txtms) {
        this.txtms = txtms;
    }

    public String getTenBh() {
        return tenBh;
    }

    public void setTenBh(String tenBh) {
        this.tenBh = tenBh;
    }

    public String getTxtcs() {
        return txtcs;
    }

    public void setTxtcs(String txtcs) {
        this.txtcs = txtcs;
    }

    public String getTxtLr() {
        return txtLr;
    }

    public void setTxtLr(String txtLr) {
        this.txtLr = txtLr;
    }

    public Boolean getThich() {
        return thich;
    }

    public void setThich(Boolean thich) {
        this.thich = thich;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}