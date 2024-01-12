package com.assetsms.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtil {
    public static void writeObject(Object obj,String fileName) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("config/"+fileName+".dat")))
        {
            out.writeObject(obj);
        }
    }
    public static Object readObject(String fileName) throws Exception{
        Object obj=null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("config/"+fileName+".dat")))
        {
            obj=in.readObject();
        }
       return obj;
    }
}
