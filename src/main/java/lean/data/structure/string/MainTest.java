package lean.data.structure.string;

import java.io.UnsupportedEncodingException;

/**
 * Created by 浩 on 2016/11/9.
 */
public class MainTest {


    public static byte[] hexStringToBytes(String hexString){
        if(hexString==null||hexString.equals("")){
            return null;
        }
        hexString=hexString.toUpperCase();
        int length=hexString.length()/2;
        char[]hexChars=hexString.toCharArray();
        byte[]d=new byte[length];
        for(int i=0;i<length;i++){
            int pos=i*2;
            d[i]=(byte)(charToByte(hexChars[pos])<<4|charToByte(hexChars[pos+1]));
            System.out.println(d[i]);
        }
        return d;
    }

    public static byte charToByte(char c){
        return(byte)"0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        String chars = "0123456789ABCDEF";
        String hexStr = "23";
        char[] array = hexStr.toUpperCase().toCharArray();
        byte[] bytes = new byte[array.length / 2];
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = (byte) (((byte)chars.indexOf(array[i*2])<<4)|((byte)chars.indexOf(array[i*2+1])));
            Integer integer = Integer.valueOf(bytes[i]);
            System.out.println();
        }
    }
}
