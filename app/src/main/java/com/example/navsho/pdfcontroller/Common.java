package com.example.navsho.pdfcontroller;

import android.content.Context;

import com.example.navsho.R;

import java.io.File;

public class Common {
    public static String getAppPath(Context context){
        File dir = new File(File.separator + "data" + File.separator + "data"
                + File.separator
                + "com.example.navsho"
                + File.separator
        );
        if(!dir.exists())
            dir.mkdir();
            return dir.getPath() + File.separator;
    }
}
