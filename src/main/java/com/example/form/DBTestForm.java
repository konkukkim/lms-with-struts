package com.example.form;

import org.apache.struts.action.ActionForm;
import java.util.ArrayList;
import java.util.List;

public class DBTestForm extends ActionForm {

    private List<String[]> results;

    public List<String[]> getResults() {
        return results;
    }

    public void setResults(List<String[]> results) {
        this.results = results;
    }

    public DBTestForm() {
        //기본 생성자 추가.
    }
}