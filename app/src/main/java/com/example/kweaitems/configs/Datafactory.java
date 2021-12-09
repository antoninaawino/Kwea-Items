package com.example.kweaitems.configs;

import com.example.kweaitems.repositories.KweaRepo;

public class Datafactory {

    private static KweaRepo kweaRepo;

    public static KweaRepo getKweaRepo() {
        if (kweaRepo == null) {
            kweaRepo = new KweaRepo();
        }
        return kweaRepo;
    }

    public static void setKweaRepo(KweaRepo kweaRepo) {
        Datafactory.kweaRepo = kweaRepo;
    }

    private static String token;

    public static String getToken() {
        if (token == null) {
            token = "";
        }
        return token;
    }

    public static void setToken(String token) {
        Datafactory.token = token;
    }
}
