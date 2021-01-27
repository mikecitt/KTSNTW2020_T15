package com.example.culturecontentapp.constants;

public class CulturalOfferConstants {

    public static final Integer PAGEABLE_SIZE_ZERO = 0;
    public static final Integer PAGEABLE_SIZE_ONE = 1;
    public static final Integer PAGEABLE_SIZE_TWO = 2;
    public static final Integer PAGEABLE_PAGE_ZERO = 0;
    public static final Integer PAGEABLE_PAGE_ONE = 1;

    public static final String CULTURAL_OFFER_NAME_EXISTS = "Sea Dance festival1";
    public static final String CULTURAL_OFFER_NAME_EXISTS_2 = "Exit festival1";
    public static final String CULTURAL_OFFER_NAME_NOT_EXISTS = "Manifestacija ne postoji";
    public static final String CULTURAL_OFFER_NAME_NEW = "Nova manifestacija";

    public static final String CULTURAL_OFFER_DESCRIPTION = "Ovo je neka deskripcija";
    public static final String CULTURAL_OFFER_LOCATION_ADDRESS = "Ovo je neka lokacija";
    public static final Double CULTURAL_OFFER_LOCATION_LONGITUDE = 20d;
    public static final Double CULTURAL_OFFER_LOCATION_LATITUDE = 50d;

    public static final Long CULTURAL_OFFER_SUBTYPE = 1L;
    public static final Long CULTURAL_OFFER_SUBTYPE_NOT_EXISTS = 5L;

    public static final Long CULTURAL_OFFER_ID_EXISTS = 1L;
    public static final Long CULTURAL_OFFER_ID_NOT_EXISTS = 5L;

    public static final String DB_ADMIN_EMAIL = "admin1@example.com";
    public static final String DB_ADMIN_PASSWORD = "qwerty";

    public static final String STORAGE_FILE_NOT_VALID = "SOME INVALID BASE64 STRING";
    public static final String STORAGE_PATH_NOT_SUPPORTED = System.getProperty("user.dir")
            + "/src/test/java/com/example/culturecontentapp/images/not_supported.svg";
    public static final String STORAGE_PATH_VALID = System.getProperty("user.dir")
            + "/src/test/java/com/example/culturecontentapp/images/valid.png";
}
