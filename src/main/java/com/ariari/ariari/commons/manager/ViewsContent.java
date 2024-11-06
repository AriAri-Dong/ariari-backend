package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.enums.ViewsContentType;

public interface ViewsContent {

    Long getId();

    Long getViews();

    void addViews(long n);

    ViewsContentType getViewsContentType();

}
