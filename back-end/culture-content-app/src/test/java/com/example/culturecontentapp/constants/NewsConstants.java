package com.example.culturecontentapp.constants;

import java.time.LocalDateTime;

public class NewsConstants {

    public static final Long NEWS_ID = 1l;
    public static final Long OFFER_ID = 1l;

    public static final Long BAD_OFFER_ID = -1l;
    public static final Long BAD_NEWS_ID = -1l;

    public static final Integer PAGEABLE_PAGE = 0;
    public static final Integer PAGEABLE_SIZE = 10;
    public static final Integer PAGEABLE_TOTAL_ELEMENTS = 10;

    public static final long DB_NEWS_SIZE = 1;

    public static final String DB_NEWS = "Najnovija vest o festivalu";
    public static final String NEWS = "blablablabla";
    public static final LocalDateTime NEWS_TIME = LocalDateTime.of(2017, 1, 14, 10, 34);

    public static final String DB_ADMIN_EMAIL = "admin@example.com";
    public static final String DB_ADMIN_PASSWORD = "qwerty";
    
}
