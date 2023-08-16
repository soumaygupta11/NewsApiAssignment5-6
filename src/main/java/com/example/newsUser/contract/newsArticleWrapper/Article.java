package com.example.newsUser.contract.newsArticleWrapper;

public class Article {
    private SourceArticle source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String content;
    private String publishedAt;

    public SourceArticle getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getContent() {
        return content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public Article(SourceArticle source, String author, String title, String description, String url, String urlToImage, String content, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
        this.publishedAt = publishedAt;
    }

    public Article() {
    }

    public void setSource(SourceArticle source) {
        this.source = source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
