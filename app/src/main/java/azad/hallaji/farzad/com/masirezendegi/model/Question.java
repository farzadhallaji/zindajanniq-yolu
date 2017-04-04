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

    public Question(String QID, String subjectID, String questionSubject, String text, String regTime, String answerCount) {
        this.QID = QID;
        SubjectID = subjectID;
        QuestionSubject = questionSubject;
        Text = text;
        RegTime = regTime;
        AnswerCount = answerCount;
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
