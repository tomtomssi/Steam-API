package com.calicode.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.helper.IReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

/**
 * @author World
 */
public class SSHReadFile {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String HOSTNAME = "hostname";
    private static final String PORT = "port";
    private static final String REMOTE_FILE_LOCATION = "remote_file";

    private LinkedHashMap<String, String> loginFileContents;
    private String remoteFileContents;
    private static final String LOGIN_DETAILS_FILE_LOCATION = "D:\\RedditTests\\information\\login.passwd";

    public SSHReadFile() {
        try {
            String fileContentsString  = new IReader.FileReader(
                    new FileReader(LOGIN_DETAILS_FILE_LOCATION))
                    .getFileContents();
            loginFileContents = parseLoginDetailsToArray(fileContentsString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(
                    loginFileContents.get(USERNAME),
                    loginFileContents.get(HOSTNAME),
                    Integer.parseInt(loginFileContents.get(PORT)));

            session.setPassword(loginFileContents.get(PASSWORD));

            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");

            session.connect();
            System.out.println("Connection established.");
            System.out.println("Creating SFTP Channel.");

            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            System.out.println("SFTP Channel created.");

            InputStream out = sftpChannel.get(loginFileContents.get(REMOTE_FILE_LOCATION));

            remoteFileContents = new IReader.FileReader(new InputStreamReader(out)).getFileContents();
            System.out.println(remoteFileContents);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private LinkedHashMap<String, String> parseLoginDetailsToArray(final String stringToParse){
        return new LinkedHashMap<String, String>(){{
            for(String str : stringToParse.split("\\r\\n")){
                String[] values = parseLine(str);
                put(values[0], values[1]);
            }
        }};
    }

    private String[] parseLine(String line){
        String[] str = { line.split("=")[0], line.split("=")[1] };
        return str;
    }
}