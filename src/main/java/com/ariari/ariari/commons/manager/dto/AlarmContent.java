package com.ariari.ariari.commons.manager.dto;

import lombok.Getter;

@Getter
public class AlarmContent {

    private final String title;
    private final String uri;

    private AlarmContent(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

    public static AlarmContent from(String title, String uri){
        return new AlarmContent(title,uri);
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }
}
