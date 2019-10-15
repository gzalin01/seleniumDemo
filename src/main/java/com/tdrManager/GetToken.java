package com.tdrManager;

import java.io.*;

public class GetToken {
    public static String getToken() {
        File file = new File( "D:\\ALIN\\selenium\\token.txt" );
        String token;
        if (file.isFile() && file.exists()) {
            InputStreamReader isr = null;
            try {
                isr = new InputStreamReader( new FileInputStream( file ), "utf-8" );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader( isr );
            try {
                token = br.readLine();
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "未获取到token";
    }
}

