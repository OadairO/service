package com.supermap.portal.too;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
/**
* @ClassName: FileToo 
* @Description: 文件操作工具
* @author yuyao
* @date 2018年3月26日 下午5:06:46
 */
public class FileToo {

	/**
	* @Title: copy 
	* @Description: 文件复制
	* @param @param src
	* @param @param des
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
    public static void copy(String src, String des) throws Exception {
        //初始化文件复制
        File file1=new File(src);
        //把文件里面内容放进数组
        File[] fs=file1.listFiles();
        //初始化文件粘贴
        File file2=new File(des);
        //判断是否有这个文件有不管没有创建
        if(!file2.exists()){
            file2.mkdirs();
        }
        //遍历文件及文件夹
        for (File f : fs) {
            if(f.isFile()){
                //文件
                fileCopy(f.getPath(),des+"/"+f.getName()); //调用文件拷贝的方法
            }else if(f.isDirectory()){
                //文件夹
                copy(f.getPath(),des+"/"+f.getName());//继续调用复制方法      递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
            }
        }
        
    }

    /**
    * @Title: fileCopy 
    * @Description: 文件复制的具体方法
    * @param @param src
    * @param @param des
    * @param @throws Exception    设定文件 
    * @return void    返回类型 
    * @throws
     */
    private static void fileCopy(String src, String des) throws Exception {
        //io流固定格式
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
        int i = -1;//记录获取长度
        byte[] bt = new byte[2014];//缓冲区
        while ((i = bis.read(bt))!=-1) {
            bos.write(bt, 0, i);
        }
        bis.close();
        bos.close();
        //关闭流
    }
    /**
    * @Title: delAllFile 
    * @Description: 删除指定文件夹下的所有文件
    * @param @param path
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public static boolean delAllFile(String path) {  
        boolean flag = false;  
        File file = new File(path);  
        if (!file.exists()) {  
          return flag;  
        }  
        if (!file.isDirectory()) {  
          return flag;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
           if (path.endsWith(File.separator)) {  
              temp = new File(path + tempList[i]);  
           } else {  
               temp = new File(path + File.separator + tempList[i]);  
           }  
           if (temp.isFile()) {  
              temp.delete();  
           }  
           if (temp.isDirectory()) {  
              delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
              delFolder(path + "/" + tempList[i]);//再删除空文件夹  
              flag = true;  
           }  
        }  
        return flag;  
      }  
    /**
    * @Title: delFolder 
    * @Description: 删除文件夹 
    * @param @param folderPath    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public static void delFolder(String folderPath) {  
        try {  
           delAllFile(folderPath); //删除完里面所有内容  
           String filePath = folderPath;  
           filePath = filePath.toString();  
           java.io.File myFilePath = new java.io.File(filePath);  
           myFilePath.delete(); //删除空文件夹  
        } catch (Exception e) {  
          e.printStackTrace();   
        }  
   }  
    
    /**
    * @Title: createJsonFile 
    * @Description: 生成.json格式文件
    * @param @param jsonString
    * @param @param filePath
    * @param @param fileName
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public static boolean createJsonFile(String jsonString, String filePath, String fileName) {
        // 标记文件生成是否成功
        boolean flag = true;

        // 拼接文件完整路径
        String fullPath = filePath + File.separator + fileName + ".json";

        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            // 格式化json字符串
            jsonString = formatJson(jsonString);

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
    
    /**
     * 单位缩进字符串。
     */
    private static String SPACE = "   ";

    /**
     * 返回格式化JSON字符串。
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    public static String formatJson(String json) {
        StringBuffer result = new StringBuffer();

        int length = json.length();
        int number = 0;
        char key = 0;

        // 遍历输入字符串。
        for (int i = 0; i < length; i++) {
            // 1、获取当前字符。
            key = json.charAt(i);

            // 2、如果当前字符是前方括号、前花括号做如下处理：
            if ((key == '[') || (key == '{')) {
                // （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }

                // （2）打印：当前字符。
                result.append(key);

                // （3）前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');

                // （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                result.append(indent(number));

                // （5）进行下一次循环。
                continue;
            }

            // 3、如果当前字符是后方括号、后花括号做如下处理：
            if ((key == ']') || (key == '}')) {
                // （1）后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');

                // （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                result.append(indent(number));

                // （3）打印：当前字符。
                result.append(key);

                // （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }

                // （5）继续下一次循环。
                continue;
            }
            // 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
            if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }
            // 5、打印：当前字符。
            result.append(key);
        }
        return result.toString();
    }

    /**
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
     * 
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    private static String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }
}
