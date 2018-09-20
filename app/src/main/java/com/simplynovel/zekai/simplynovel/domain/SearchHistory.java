package com.simplynovel.zekai.simplynovel.domain;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 15082 on 2018/9/9.
 */

public class SearchHistory extends LitePalSupport {
    String bookName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
