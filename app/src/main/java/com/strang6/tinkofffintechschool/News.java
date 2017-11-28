package com.strang6.tinkofffintechschool;

import java.util.Date;

/**
 * Created by Strang6 on 27.11.2017.
 */

public class News {
    private String text;
    private Date publicationDate;

    public News(String text, Date publicationDate) {
        this.text = text;
        this.publicationDate = publicationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
