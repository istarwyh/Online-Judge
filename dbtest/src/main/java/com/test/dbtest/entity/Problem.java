package com.test.dbtest.main.entity;

import java.util.List;

public class Problem {
    private int id;
    private String title;
    private String context;
    private int difficulty;
    private List<String> problemcontext;

    public List<String> getProblemcontext() {
        return problemcontext;
    }

    public void setProblemcontext(List<String> problemcontext) {
        this.problemcontext = problemcontext;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int diffuculty) {
        this.difficulty = diffuculty;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}
