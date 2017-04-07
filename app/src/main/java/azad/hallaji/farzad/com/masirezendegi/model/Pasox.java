package azad.hallaji.farzad.com.masirezendegi.model;

import java.util.PriorityQueue;

/**
 * Created by fayzad on 4/7/17.
 */

public class Pasox {

    String userimg,Name ,Matnepasox,CountLike , CountdisLike , TarixeJavab;

    public Pasox(String userimg, String name, String matnepasox, String countLike, String countdisLike, String tarixeJavab) {
        this.userimg = userimg;
        Name = name;
        Matnepasox = matnepasox;
        CountLike = countLike;
        CountdisLike = countdisLike;
        TarixeJavab = tarixeJavab;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMatnepasox() {
        return Matnepasox;
    }

    public void setMatnepasox(String matnepasox) {
        Matnepasox = matnepasox;
    }

    public String getCountLike() {
        return CountLike;
    }

    public void setCountLike(String countLike) {
        CountLike = countLike;
    }

    public String getCountdisLike() {
        return CountdisLike;
    }

    public void setCountdisLike(String countdisLike) {
        CountdisLike = countdisLike;
    }

    public String getTarixeJavab() {
        return TarixeJavab;
    }

    public void setTarixeJavab(String tarixeJavab) {
        TarixeJavab = tarixeJavab;
    }
}
