package azad.hallaji.farzad.com.masirezendegi.model;

import java.io.Serializable;

/**
 * Created by fayzad on 3/24/17.
 */

public class Question implements Serializable{

    String QID;
    String SubjectID;
    String QuestionSubject;
    String Text;
    String RegTime;
    String AnswerCount;
    String LikeCount;
    String DisLikeCount;

    public String getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(String likeCount) {
        LikeCount = likeCount;
    }

    public String getDisLikeCount() {
        return DisLikeCount;
    }

    public void setDisLikeCount(String disLikeCount) {
        DisLikeCount = disLikeCount;
    }

    public Question(String QID, String subjectID, String questionSubject, String text, String regTime, String answerCount) {
        this.QID = QID;
        SubjectID = subjectID;
        QuestionSubject = questionSubject;
        Text = text;
        RegTime = regTime;
        AnswerCount = answerCount;
    }
    public Question(int n,String QID, String questionSubject, String text, String regTime, String LikeCount , String DisLikeCount, String AnswerCount) {
        this.QID = QID;
        QuestionSubject = questionSubject;
        this.AnswerCount=AnswerCount;
        Text = text;
        RegTime = regTime;
        this.LikeCount = LikeCount ;
        this.DisLikeCount = DisLikeCount ;

    }

    public String getQID() {
        return QID;
    }

    public void setQID(String QID) {
        this.QID = QID;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getQuestionSubject() {
        return QuestionSubject;
    }

    public void setQuestionSubject(String questionSubject) {
        QuestionSubject = questionSubject;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getRegTime() {
        return RegTime;
    }

    public void setRegTime(String regTime) {
        RegTime = regTime;
    }

    public String getAnswerCount() {
        return AnswerCount;
    }

    public void setAnswerCount(String answerCount) {
        AnswerCount = answerCount;
    }
}
