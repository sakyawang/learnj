package lean.img;


import com.asprise.ocr.Ocr;

import java.io.File;

/**
 * Created by 浩 on 2016/9/7.
 */
public class ImgVerify {

    public static void main(String[] args) {

        Ocr.setUp();
        Ocr ocr = new Ocr();
        ocr.startEngine("eng",Ocr.SPEED_FAST);
        String s = ocr.recognize(new File[]{new File("E:/checknum.png")}, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        System.out.println(s);
        ocr.stopEngine();
    }
}
