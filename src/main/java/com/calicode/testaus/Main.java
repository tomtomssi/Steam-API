package com.calicode.testaus;

import com.github.koraktor.steamcondenser.community.AppNews;
import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import tatu.ssh.SSHReadFile;

import java.util.List;

public class Main {

    public static void main(String [] args) {
        SSHReadFile sshReadFile = new SSHReadFile();
        try {
            List<AppNews> appNews = AppNews.getNewsForApp(730);
            for (AppNews appNew : appNews){
                System.out.println(appNew.getContents());
            }
        } catch (WebApiException e) {
            e.printStackTrace();
        }
    }
}
