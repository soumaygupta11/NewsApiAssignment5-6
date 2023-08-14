package com.example.newsUser.contract.sourceWrapper;

import java.util.List;

public class ListSource {
    @Override
    public String toString() {
        return "ListSource{" +
                "status='" + status + '\'' +
                ", sources=" + sources +
                '}';
    }

    private String status;
    private List<Source> sources;

    public String getStatus() {
        return status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public ListSource() {
    }

    public ListSource(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }
}
