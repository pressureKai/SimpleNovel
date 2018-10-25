package com.simplynovel.zekai.simplynovel.ui.pageWidget.util;

import android.content.ContentValues;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.simplynovel.zekai.simplynovel.ui.pageWidget.bean.Cache;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.db.BookCatalogue;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.db.BookList;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class BookUtil {
    private static final String cachedPath = Environment.getExternalStorageDirectory() + "/treader/";
    //存储的字符数
    public static final int cachedSize = 30000;

    protected final ArrayList<Cache> myArray = new ArrayList<>();
    //目录
    private List<BookCatalogue> directoryList = new ArrayList<>();

    private String m_strCharsetName;
    private String bookName;
    private String bookPath;
    private long bookLen;
    private long position;
    private BookList bookList;

    public BookUtil(){
        File file = new File(cachedPath);
        if (!file.exists()){
            file.mkdir();
        }
    }

    public synchronized void openBook(BookList bookList) throws IOException {
        this.bookList = bookList;
        //如果当前缓存不是要打开的书本就缓存书本同时删除缓存
        if (bookPath == null || !bookPath.equals(bookList.getBookpath())) {
            cleanCacheFile();
            this.bookPath = bookList.getBookpath();
            bookName = FileUtils.getFileName(bookPath);
            cacheBook();
        }
    }

    private void cleanCacheFile(){
        File file = new File(cachedPath);
        if (!file.exists()){
            file.mkdir();
        }else{
            File[] files = file.listFiles();
            for (int i = 0; i < files.length;i++){
                files[i].delete();
            }
        }
    }

    public int next(boolean back){
        position += 1;
        if (position > bookLen){
            position = bookLen;
            return -1;
        }
        char result = current();
        if (back) {
            position -= 1;
        }
        return result;
    }

    public char[] preLine(){
        if (position <= 0){
            return null;
        }
        String line = "";
        while (position >= 0){
            int word = pre(false);
            if (word == -1){
                break;
            }
            char wordChar = (char) word;
            if ((wordChar + "").equals("\n") && (((char)pre(true)) + "").equals("\r")){
                pre(false);
                break;
            }
            line = wordChar + line;
        }
        return line.toCharArray();
    }


    public char current(){
        int cachePos = 0;
        int pos = 0;
        int len = 0;
        for (int i = 0;i < myArray.size();i++){
            long size = myArray.get(i).getSize();
            if (size + len - 1 >= position){
                cachePos = i;
                pos = (int) (position - len);
                break;
            }
            len += size;
        }

        char[] charArray = block(cachePos);
        return charArray[pos];
    }

    public int pre(boolean back){
        position -= 1;
        if (position < 0){
            position = 0;
            return -1;
        }
        char result = current();
        if (back) {
            position += 1;
        }
        return result;
    }

    public long getPosition(){
        return position;
    }

    public void setPostition(long position){
        this.position = position;
    }

    //缓存书本
    private void cacheBook() throws IOException {
        if (TextUtils.isEmpty(bookList.getCharset())) {
            m_strCharsetName = FileUtils.getCharset(bookPath);
            if (m_strCharsetName == null) {
                m_strCharsetName = "utf-8";
            }
            ContentValues values = new ContentValues();
            values.put("charset",m_strCharsetName);
            DataSupport.update(BookList.class,values,bookList.getId());
        }else{
            m_strCharsetName = bookList.getCharset();
        }
        // 打开文件新建一个file用于新建 InputStreamReader 文件输入流
        File file = new File(bookPath);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file),m_strCharsetName);
        int index = 0;
        bookLen = 0;
        directoryList.clear();
        myArray.clear();
        while (true){
            char[] buf = new char[cachedSize];
            int result = reader.read(buf);
            if (result == -1){
                reader.close();
                break;
            }

            String bufStr = new String(buf);
            //消除空白行 以空格取代
            bufStr = bufStr.replaceAll("\r\n+\\s*","\r\n\u3000\u3000");
            //消除空格
            bufStr = bufStr.replaceAll("\u0000","");
            buf = bufStr.toCharArray();
            bookLen += buf.length;

            Cache cache = new Cache();
            cache.setSize(buf.length);
            // 将数据包装到Cache类
            cache.setData(new WeakReference<>(buf));
            myArray.add(cache);
            try {
                File cacheBook = new File(fileName(index));
                if (!cacheBook.exists()){
                    cacheBook.createNewFile();
                }
                final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName(index)), "UTF-16LE");
                writer.write(buf);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException("Error during writing " + fileName(index));
            }
            index ++;
        }

        new Thread(){
            @Override
            public void run() {
                getChapter();
            }
        }.start();
    }

    //获取章节
    public synchronized void getChapter(){
        try {
            long size = 0;
            for (int i = 0; i < myArray.size(); i++) {
                char[] buf = block(i);
                String bufStr = new String(buf);
                String[] paragraphs = bufStr.split("\r\n");
                for (String str : paragraphs) {
                    if (str.length() <= 30 && (str.matches(".*第.{1,8}章.*") || str.matches(".*第.{1,8}节.*"))) {
                        BookCatalogue bookCatalogue = new BookCatalogue();
                        bookCatalogue.setBookCatalogueStartPos(size);
                        bookCatalogue.setBookCatalogue(str);
                        bookCatalogue.setBookpath(bookPath);
                        directoryList.add(bookCatalogue);
                    }
                    if (str.contains("\u3000\u3000")) {
                        size += str.length() + 2;
                    }else if (str.contains("\u3000")){
                        size += str.length() + 1;
                    }else {
                        size += str.length();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<BookCatalogue> getDirectoryList(){
        return directoryList;
    }

    public long getBookLen(){
        return bookLen;
    }

    protected String fileName(int index) {
        return cachedPath + bookName + index ;
    }

    //获取书本缓存
    public char[] block(int index) {
        if (myArray.size() == 0){
            return new char[1];
        }
        // get char[]
        char[] block = myArray.get(index).getData().get();
        if (block == null) {
            try {
                File file = new File(fileName(index));
                int size = (int)file.length();
                if (size < 0) {
                    throw new RuntimeException("Error during reading " + fileName(index));
                }
                block = new char[size / 2];
                InputStreamReader reader =
                        new InputStreamReader(
                                new FileInputStream(file),
                                "UTF-16LE"
                        );
                if (reader.read(block) != block.length) {
                    throw new RuntimeException("Error during reading " + fileName(index));
                }
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException("Error during reading " + fileName(index));
            }
            Cache cache = myArray.get(index);
            cache.setData(new WeakReference<char[]>(block));
        }
        return block;
    }

}
