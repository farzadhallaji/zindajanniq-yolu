package azad.hallaji.farzad.com.masirezendegi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fayzad on 4/23/17.
 */

public class ALagemandi {

    private List<Moshaver> moshavers=new ArrayList<>();
    private List<Markaz> markazs=new ArrayList<>();
    private List<Question> questions=new ArrayList<>();

    public ALagemandi(List<Moshaver> moshavers, List<Markaz> markazs, List<Question> questions) {
        this.moshavers = moshavers;
        this.markazs = markazs;
        this.questions = questions;
    }

    public ALagemandi() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Moshaver> getMoshavers() {
        return moshavers;
    }

    public void setMoshavers(List<Moshaver> moshavers) {
        this.moshavers = moshavers;
    }

    public List<Markaz> getMarkazs() {
        return markazs;
    }

    public void setMarkazs(List<Markaz> markazs) {
        this.markazs = markazs;
    }
}
