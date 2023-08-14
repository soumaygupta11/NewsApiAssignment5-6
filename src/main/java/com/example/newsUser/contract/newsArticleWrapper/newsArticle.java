package com.example.newsUser.contract.newsArticleWrapper;

import java.util.List;

public class newsArticle {
    private String status;
    private int totalResults;
    private List<Article> articles;


    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public newsArticle(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public newsArticle() {
    }
}
