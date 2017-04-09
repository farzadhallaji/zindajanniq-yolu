package azad.hallaji.farzad.com.masirezendegi.model;


/**
 * Created by fayzad on 4/10/17.
 */

public class Comment  {

    String comment,RegTime ,UserName,UserFamilyName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRegTime() {
        return RegTime;
    }

    public void setRegTime(String regTime) {
        RegTime = regTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserFamilyName() {
        return UserFamilyName;
    }

    public void setUserFamilyName(String userFamilyName) {
        UserFamilyName = userFamilyName;
    }

    public Comment(String comment, String regTime, String userName, String userFamilyName) {

        this.comment = comment;
        RegTime = regTime;
        UserName = userName;
        UserFamilyName = userFamilyName;
    }
}
