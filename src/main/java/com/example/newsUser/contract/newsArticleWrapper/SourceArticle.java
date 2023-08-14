package com.example.newsUser.contract.newsArticleWrapper;

import com.example.newsUser.contract.sourceWrapper.Source;

public class SourceArticle {
    private String id;
    private String name;

    public SourceArticle(Source source){
        this.id = source.getId();
        this.name = source.getName();
    }
    public SourceArticle(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public SourceArticle() {
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
