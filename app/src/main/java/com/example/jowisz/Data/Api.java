package com.example.jowisz.Data;

public class Api {

    private static final String ROOT_URL = "http://192.168.0.227:8080/ShopApi/v1/Api.php?apicall="; //Wiktor - dom
//    private static final String ROOT_URL = "http://10.182.121.4:8080/ShopApi/v1/Api.php?apicall=";  //uczelnia
//    private static final String ROOT_URL = "http://192.168.64.2:80/ShopApi/v1/Api.php?apicall=";

    public static final String URL_READ_SPRZET = ROOT_URL + "getsprzet";
    public static final String URL_CREATE_KLIENT = ROOT_URL + "createklient";
    public static final String URL_READ_KATEGORIE = ROOT_URL + "getkategorie";
    public static final String URL_READ_SPRZET_Z_KATEGORII = ROOT_URL + "getsprzetzkategorii&IdTyp=";
//    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
//    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";

}
