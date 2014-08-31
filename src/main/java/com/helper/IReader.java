package com.helper;

import java.io.BufferedReader;
import java.io.IOException;

public interface IReader {
    String getFileContents();

    public class FileReader extends BufferedReader implements IReader{
        private String fileContents;

        public FileReader(java.io.Reader reader) {
            super(reader);
            getContents();
        }

        private void getContents(){
            try{
                StringBuilder stringBuilder = new StringBuilder();
                String line = readLine();

                while (line != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                    line = readLine();
                }
                fileContents = stringBuilder.toString();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }

        public String getFileContents() {
            return fileContents;
        }
    }
}
