package lean.file;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/4/29
 * Time: 18:57
 */
public class RemoveFile {

    private String currentDir = "";

    public static void remove(String path){

        File file = new File(path);
        if(file.isDirectory()){
            String[] list = file.list();
            if(file.list().length == 0){
                file.deleteOnExit();
            }else{
                for(String s : list){
                    remove(path + "/" + s);
                }
            }
        }else{
            file.deleteOnExit();
        }
    }

    public static void main(String[] args) {

        remove("D:/360Downloads");
    }
}
