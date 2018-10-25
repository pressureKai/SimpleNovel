package com.simplynovel.zekai.simplynovel.ui.pageWidget.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;


import com.simplynovel.zekai.simplynovel.R;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.Config;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.PageWidget;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.db.BookCatalogue;
import com.simplynovel.zekai.simplynovel.ui.pageWidget.db.BookList;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class PageFactory {
    private static PageFactory pageFactory;
    private long count = 0;
    private int pageIndex;
    private Context mContext;
    private Config config;
    //页面宽
    private int mWidth;
    //页面高
    private int mHeight;
    //文字字体大小
    private float m_fontSize;
    //时间格式
    private SimpleDateFormat sdf;
    //时间
    private String date;
    //进度格式时间
    private DecimalFormat df;
    //进度格式时间章节页数
    private DecimalFormat df2;
    //电池边界宽度
    private float mBorderWidth;
    // 上下与边缘的距离
    private float marginHeight;
    // 左右与边缘的距离
    private float measureMarginWidth;
    // 左右与边缘的距离
    private float marginWidth;
    //状态栏距离底部高度
    private float statusMarginBottom;
    //行间距
    private float lineSpace;
    //段间距
    private float paragraphSpace;
    //字高度
    private float fontHeight;
    //字体
    private Typeface typeface;
    //文字画笔
    private Paint mPaint;
    //加载画笔
    private Paint waitPaint;
    // old font size
    private float mOldFontSize = 0;
    //文字颜色
    private int m_textColor = Color.rgb(50, 65, 78);
    // 绘制内容的宽
    private float mVisibleHeight;
    // 绘制内容的宽
    private float mVisibleWidth;
    // 每页可以显示的行数
    private int mLineCount;
    //电池画笔
    private Paint mBatterryPaint;
    //电池字体大小
    private float mBatterryFontSize;
    //背景图片
    private Bitmap m_book_bg = null;
    private Intent batteryInfoIntent;
    //电池电量百分比
    private float mBatteryPercentage;
    //电池外边框
    private RectF rect1 = new RectF();
    //电池内边框
    private RectF rect2 = new RectF();
    //当前是否为第一页
    private boolean m_isfirstPage;
    //当前是否为最后一页
    private boolean m_islastPage;
    //书本widget
    private PageWidget mBookPageWidget;
    //书本路径
    private String bookPath = "";
    //书本名字
    private String bookName = "";
    private BookList bookList;
    //书本章节
    private int currentCharter = 0;
    //当前电量
    private int level = 0;
    private BookUtil mBookUtil;
    private TRPage currentPage;
    private TRPage prePage;
    private TRPage cancelPage;
    private BookTask bookTask;
    ContentValues values = new ContentValues();

    private static Status mStatus = Status.OPENING;

    // 枚举状态
    public enum Status {
        OPENING,
        FINISH,
        FAIL,
    }

    public static synchronized PageFactory getInstance() {
        return pageFactory;
    }

    public static synchronized PageFactory createPageFactory(Context context) {
        if (pageFactory == null) {
            pageFactory = new PageFactory(context);
        }
        return pageFactory;
    }


    /**
     * PageFactory() 构造方法
     *
     * @param context
     */
    private PageFactory(Context context) {
        mBookUtil = new BookUtil();
        mContext = context.getApplicationContext();
        config = Config.getInstance();


        //获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        mWidth = metric.widthPixels;
        mHeight = metric.heightPixels;

        sdf = new SimpleDateFormat("HH:mm");//HH:mm为24小时制,hh:mm为12小时制
        date = sdf.format(new java.util.Date());
        df = new DecimalFormat("#0.0");

        marginWidth = mContext.getResources().getDimension(R.dimen.readingMarginWidth);
        marginHeight = mContext.getResources().getDimension(R.dimen.readingMarginHeight);
        statusMarginBottom = mContext.getResources().getDimension(R.dimen.reading_status_margin_bottom);
        lineSpace = context.getResources().getDimension(R.dimen.reading_line_spacing);
        paragraphSpace = context.getResources().getDimension(R.dimen.reading_paragraph_spacing);
        mVisibleWidth = mWidth - marginWidth * 2;
        mVisibleHeight = mHeight - marginHeight * 2;

        typeface = config.getTypeface();
        m_fontSize = config.getFontSize();

        //the first paint  文字画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
        mPaint.setTextAlign(Paint.Align.LEFT);// 左对齐
        mPaint.setTextSize(m_fontSize);// 字体大小
        mPaint.setColor(m_textColor);// 字体颜色
        mPaint.setTypeface(typeface);
        mPaint.setSubpixelText(true);// 设置该项为true，将有助于文本在LCD屏幕上的显示效果

        //the second paint 加载画笔
        waitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
        waitPaint.setTextAlign(Paint.Align.LEFT);// 左对齐
        waitPaint.setTextSize(mContext.getResources().getDimension(R.dimen.reading_max_text_size));// 字体大小
        waitPaint.setColor(m_textColor);// 字体颜色
        waitPaint.setTypeface(typeface);
        waitPaint.setSubpixelText(true);// 设置该项为true，将有助于文本在LCD屏幕上的显示效果

        //计算可显示的行数
        calculateLineCount();

        //显示时间的边界宽度
        mBorderWidth = mContext.getResources().getDimension(R.dimen.reading_board_battery_border_width);

        // 3 电池画笔
        mBatterryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBatterryFontSize = CommonUtil.sp2px(context, 12);
        mBatterryPaint.setTextSize(mBatterryFontSize);
        mBatterryPaint.setTypeface(typeface);
        mBatterryPaint.setTextAlign(Paint.Align.LEFT);
        mBatterryPaint.setColor(m_textColor);
        batteryInfoIntent = context.getApplicationContext().registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));//注册广播,随时获取到电池电量信息
        initBg(config.getDayOrNight());
        // 测量边距
        measureMarginWidth();
    }

    private void measureMarginWidth() {
        //测量一个 中文空格  的宽度
        float wordWidth = mPaint.measureText("\u3000");
        float width = mVisibleWidth % wordWidth;
        measureMarginWidth = marginWidth + width / 2;

    }

    //初始化背景
    private void initBg(Boolean isNight) {
        if (isNight) {
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.BLACK);
            setBgBitmap(bitmap);
            //设置字体颜色
            setM_textColor(Color.rgb(128, 128, 128));
            setBookPageBg(Color.BLACK);
        } else {
            //设置背景
            setBookBg(config.getBookBgType());
        }
    }

    private void calculateLineCount() {
        mLineCount = (int) (mVisibleHeight / (m_fontSize + lineSpace));// 可显示的行数
    }

    private void drawStatus(Bitmap bitmap) {
        String status = "";
        switch (mStatus) {
            case OPENING:
                status = "加载中...";
                break;
            case FAIL:
                status = "加载失败！";
                break;
        }

        Canvas c = new Canvas(bitmap);
        c.drawBitmap(getBgBitmap(), 0, 0, null);
        waitPaint.setColor(getTextColor());

        waitPaint.setTextAlign(Paint.Align.CENTER);
        Rect targetRect = new Rect(0, 0, mWidth, mHeight);
        Paint.FontMetricsInt fontMetrics = waitPaint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        waitPaint.setTextAlign(Paint.Align.CENTER);
        c.drawText(status, targetRect.centerX(), baseline, waitPaint);

        // mBookPageWidget 当此组件被添加到窗口时，可以从非UI线程中唤醒并调用该方法
        mBookPageWidget.postInvalidate();
    }

    public void onDraw(Bitmap bitmap, List<String> m_lines, Boolean updateCharter) {
        if (getDirectoryList().size() > 0 && updateCharter) {
            currentCharter = getCurrentCharter();
        }
        //更新数据库进度
        if (currentPage != null && bookList != null) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    values.put("begin", currentPage.getBegin());
                    if (pageIndex != 0) {
                        values.put("pageindex", pageIndex);
                    }
                    DataSupport.update(BookList.class, values, 1);
                }
            }.start();
        }


        //画小说正文
        //新建一个画板
        Canvas c = new Canvas(bitmap);

        c.drawBitmap(getBgBitmap(), 0, 0, null);

        mPaint.setTextSize(getFontSize());
        mPaint.setColor(getTextColor());
        mBatterryPaint.setColor(getTextColor());
        if (m_lines.size() == 0) {
            return;
        }

        if (m_lines.size() > 0) {
            float y = marginHeight;
            for (String strLine : m_lines) {
                y += m_fontSize + lineSpace;
                c.drawText(strLine, measureMarginWidth, y, mPaint);
            }
        }

        if (count == 0) {
            while (getNextLines().size() > 0) {
                count++;
            }
            if (currentPage.getBegin() != 0) {
                int preIndex = 0;
                while (getPreLines().size() > 0) {
                    preIndex++;
                }
                count = preIndex;
            } else {
                count += 1;
            }
        } else {
            count = getPageCount();
        }

        measurePageIndex();
        //画进度及时间
        int dateWith = (int) (mBatterryPaint.measureText(date) + mBorderWidth);//时间宽度


        if (count >= 10 && count < 100) {
            df2 = new DecimalFormat("00");
        } else if (count >= 100) {
            df2 = new DecimalFormat("000");
        } else if (count < 10) {
            df2 = new DecimalFormat("0");
        }


        String strPercent = "第" + df2.format(this.pageIndex) + "/" + count + "页";//进度文字
        int nPercentWidth = (int) mBatterryPaint.measureText("第99/99页") + 1;  //Paint.measureText直接返回參數字串所佔用的寬度
        c.drawText(strPercent, mWidth - nPercentWidth, mHeight - statusMarginBottom, mBatterryPaint);//x y为坐标值
        c.drawText(date, marginWidth, mHeight - statusMarginBottom, mBatterryPaint);


        // 画电池
        level = batteryInfoIntent.getIntExtra("level", 0);
        int scale = batteryInfoIntent.getIntExtra("scale", 100);
        mBatteryPercentage = (float) level / scale;
        float rect1Left = marginWidth + dateWith + statusMarginBottom;//电池外框left位置


        //画电池外框
        float width = CommonUtil.convertDpToPixel(mContext, 20) - mBorderWidth;
        float height = CommonUtil.convertDpToPixel(mContext, 10);
        rect1.set(rect1Left, mHeight - height - statusMarginBottom, rect1Left + width, mHeight - statusMarginBottom);
        rect2.set(rect1Left + mBorderWidth, mHeight - height + mBorderWidth - statusMarginBottom, rect1Left + width - mBorderWidth, mHeight - mBorderWidth - statusMarginBottom);
        c.save(Canvas.ALL_SAVE_FLAG);
        c.clipRect(rect2, Region.Op.DIFFERENCE);
        c.drawRect(rect1, mBatterryPaint);
        c.restore();

        //画电量部分
        rect2.left += mBorderWidth;
        rect2.right -= mBorderWidth;
        rect2.right = rect2.left + rect2.width() * mBatteryPercentage;
        rect2.top += mBorderWidth;
        rect2.bottom -= mBorderWidth;
        c.drawRect(rect2, mBatterryPaint);

        //画电池头
        int poleHeight = (int) CommonUtil.convertDpToPixel(mContext, 10) / 2;
        rect2.left = rect1.right;
        rect2.top = rect2.top + poleHeight / 4;
        rect2.right = rect1.right + mBorderWidth;
        rect2.bottom = rect2.bottom - poleHeight / 4;
        c.drawRect(rect2, mBatterryPaint);

        //画书名
        c.drawText(CommonUtil.subString(bookName, 12), marginWidth, statusMarginBottom + mBatterryFontSize, mBatterryPaint);


        //画章
        if (getDirectoryList().size() > 0) {
            String charterName = CommonUtil.subString(getDirectoryList().get(currentCharter).getBookCatalogue(), 12);
            int nChaterWidth = (int) mBatterryPaint.measureText(charterName) + 1;
            c.drawText(charterName, mWidth - marginWidth - nChaterWidth, statusMarginBottom + mBatterryFontSize, mBatterryPaint);
        }

        mBookPageWidget.postInvalidate();
    }

    private void measurePageIndex() {
        int index = getPageIndex();
        if (pageIndex == 0) {
            int temp = bookList.getPageindex();
            if (temp == 0) {
                pageIndex = temp + 1;
            } else {
                if (temp >= count) {
                    pageIndex = (int) count;
                } else {
                    pageIndex = temp + 1;
                }
            }
        } else {
            // 第一页时
            if (index != 0) {
                int temp = bookList.getPageindex();
                // 在最后一页时
                if (index == count) {
                    if (temp == 0) {
                        pageIndex = temp + 1;
                    }else{
                        pageIndex = index;
                    }
                    if (pageIndex > count) {
                        pageIndex = index;
                    }
                } else {
                    if (index < count && index != 0) {
                        if(pageIndex < index ){
                            pageIndex = index;
                        }else{
                            pageIndex = index + 1;
                        }
                        if (pageIndex > count) {
                            pageIndex = index;
                        }
                    }
                }
            }else{
                if (currentPage.getBegin() == 0) {
                    pageIndex = index + 1;
                }
            }
        }
    }

    private long getPageCount() {
        if (mOldFontSize != m_fontSize && mOldFontSize != 0) {
            count = 0;
            while (getNextLines().size() > 0) {
                count++;
            }
            count = getPageIndex();
            mOldFontSize = m_fontSize;
        }
        return count;
    }

    private int getPageIndex() {
        int tempIndex = 0;
        while (getPreLines().size() > 0) {
            tempIndex++;
        }
        return tempIndex;
    }

    //向前翻页
    public void prePage() {
        if (currentPage != null) {
            if (currentPage.getBegin() <= 0) {
                if (!m_isfirstPage) {
                    Toast.makeText(mContext, "当前是第一页", Toast.LENGTH_SHORT).show();
                }
                m_isfirstPage = true;
                return;
            } else {
                m_isfirstPage = false;
            }

            cancelPage = currentPage;
            onDraw(mBookPageWidget.getCurPage(), currentPage.getLines(), true);
            currentPage = getPrePage();
            onDraw(mBookPageWidget.getNextPage(), currentPage.getLines(), true);

        } else {
            return;
        }

    }

    //向后翻页
    public void nextPage() {
        if (currentPage != null) {
            if (currentPage.getEnd() >= mBookUtil.getBookLen()) {
                if (!m_islastPage) {
                    Toast.makeText(mContext, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                }
                m_islastPage = true;
                return;
            } else {
                m_islastPage = false;
            }

            cancelPage = currentPage;
            onDraw(mBookPageWidget.getCurPage(), currentPage.getLines(), true);
            prePage = currentPage;
            currentPage = getNextPage();
            onDraw(mBookPageWidget.getNextPage(), currentPage.getLines(), true);

        } else {
            return;
        }

    }

    /**
     * PageFactory program entrance
     *
     * @param bookList book info
     * @throws IOException
     */
    public void openBook(BookList bookList,long begin) throws IOException {
        //清空数据
        currentCharter = 0;
        initBg(config.getDayOrNight());
        this.bookList = bookList;
        bookPath = bookList.getBookpath();
        bookName = FileUtils.getFileName(bookPath);
        mStatus = Status.OPENING;


        drawStatus(mBookPageWidget.getCurPage());
        drawStatus(mBookPageWidget.getNextPage());

        //如果 bookTask不为空，停止当前任务
        if (bookTask != null && bookTask.getStatus() != AsyncTask.Status.FINISHED) {
            bookTask.cancel(true);
        }

        bookTask = new BookTask();
        if(begin != 0){
            begin = 0;
            bookTask.execute(begin);
        }else{
            bookTask.execute(bookList.getBegin());
        }

    }


    private class BookTask extends AsyncTask<Long, Void, Boolean> {
        private long begin = 0;

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (isCancelled()) {
                return;
            }
            if (result) {
                PageFactory.mStatus = PageFactory.Status.FINISH;
                currentPage = getPageForBegin(begin);
                // 如果传入的pageWidget 不为空，则绘制书本内容
                if (mBookPageWidget != null) {
                    currentPage(true);
                }
            } else {
                PageFactory.mStatus = PageFactory.Status.FAIL;
                drawStatus(mBookPageWidget.getCurPage());
                drawStatus(mBookPageWidget.getNextPage());
                Toast.makeText(mContext, "加载失败！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        //可变长度 Long... params ，后台运行程序，打开某一个文件
        @Override
        protected Boolean doInBackground(Long... params) {
            begin = params[0];
            try {
                mBookUtil.openBook(bookList);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

    }

    public TRPage getNextPage() {
        mBookUtil.setPostition(currentPage.getEnd());
        TRPage trPage = new TRPage();
        trPage.setBegin(currentPage.getEnd() + 1);
        trPage.setLines(getNextLines());
        trPage.setEnd(mBookUtil.getPosition());
        return trPage;
    }

    public TRPage getPrePage() {
        mBookUtil.setPostition(currentPage.getBegin());
        TRPage trPage = new TRPage();
        trPage.setEnd(mBookUtil.getPosition() - 1);
        trPage.setLines(getPreLines());
        trPage.setBegin(mBookUtil.getPosition());
        return trPage;
    }


    /**
     * getPageForBegin 记录恢复页面信息
     *
     * @param begin
     * @return
     */
    public TRPage getPageForBegin(long begin) {
        TRPage trPage = new TRPage();
        trPage.setBegin(begin);
        mBookUtil.setPostition(begin - 1);
        trPage.setLines(getNextLines());
        trPage.setEnd(mBookUtil.getPosition());
        return trPage;
    }

    public List<String> getNextLines() {
        List<String> lines = new ArrayList<>();
        float width = 0;
        String line = "";
        while (mBookUtil.next(true) != -1) {
            char word = (char) mBookUtil.next(false);
            //判断是否换行
            if ((word + "").equals("\r") && (((char) mBookUtil.next(true)) + "").equals("\n")) {
                mBookUtil.next(false);
                if (!line.isEmpty()) {
                    lines.add(line);
                    line = "";
                    width = 0;
                    if (lines.size() == mLineCount) {
                        break;
                    }
                }
            } else {
                float widthChar = mPaint.measureText(word + "");
                width += widthChar;
                if (width > mVisibleWidth) {
                    width = widthChar;
                    lines.add(line);
                    line = word + "";
                } else {
                    line += word;
                }
            }

            if (lines.size() == mLineCount) {
                if (!line.isEmpty()) {
                    mBookUtil.setPostition(mBookUtil.getPosition() - 1);
                }
                break;
            }
        }

        if (!line.isEmpty() && lines.size() < mLineCount) {
            lines.add(line);
        }

        return lines;
    }

    public List<String> getPreLines() {
        List<String> lines = new ArrayList<>();
        float width = 0;
        String line = "";

        char[] par = mBookUtil.preLine();
        while (par != null) {
            List<String> preLines = new ArrayList<>();
            for (int i = 0; i < par.length; i++) {
                char word = par[i];
                float widthChar = mPaint.measureText(word + "");
                width += widthChar;
                if (width > mVisibleWidth) {
                    width = widthChar;
                    preLines.add(line);
                    line = word + "";
                } else {
                    line += word;
                }
            }
            if (!line.isEmpty()) {
                preLines.add(line);
            }

            lines.addAll(0, preLines);

            if (lines.size() >= mLineCount) {
                break;
            }
            width = 0;
            line = "";
            par = mBookUtil.preLine();
        }

        List<String> reLines = new ArrayList<>();
        int num = 0;
        for (int i = lines.size() - 1; i >= 0; i--) {
            if (reLines.size() < mLineCount) {
                reLines.add(0, lines.get(i));
            } else {
                num = num + lines.get(i).length();
            }
        }

        if (num > 0) {
            if (mBookUtil.getPosition() > 0) {
                mBookUtil.setPostition(mBookUtil.getPosition() + num + 2);
            } else {
                mBookUtil.setPostition(mBookUtil.getPosition() + num);
            }
        }

        return reLines;
    }

    //获取现在的章
    public int getCurrentCharter() {
        int num = 0;
        for (int i = 0; getDirectoryList().size() > i; i++) {
            BookCatalogue bookCatalogue = getDirectoryList().get(i);
            if (currentPage.getEnd() >= bookCatalogue.getBookCatalogueStartPos()) {
                num = i;
            } else {
                break;
            }
        }
        return num;
    }

    //绘制当前页面
    public void currentPage(Boolean updateChapter) {
        if (currentPage == null) {
            return;
        }
        onDraw(mBookPageWidget.getCurPage(), currentPage.getLines(), updateChapter);
        onDraw(mBookPageWidget.getNextPage(), currentPage.getLines(), updateChapter);
    }

    //更新电量
    public void updateBattery(int mLevel) {
        if (currentPage != null && mBookPageWidget != null && !mBookPageWidget.isRunning()) {
            if (level != mLevel) {
                level = mLevel;
                currentPage(false);
            }
        }
    }

    public void updateTime() {
        if (currentPage != null && mBookPageWidget != null && !mBookPageWidget.isRunning()) {
            String mDate = sdf.format(new java.util.Date());
            if (date != mDate) {
                date = mDate;
                currentPage(false);
            }
        }
    }

    //改变字体大小
    public void changeFontSize(int fontSize) {
        if (currentPage == null) {
            return;
        }
        mOldFontSize = this.m_fontSize;
        this.m_fontSize = fontSize;
        mPaint.setTextSize(m_fontSize);
        calculateLineCount();
        measureMarginWidth();
        currentPage = getPageForBegin(currentPage.getBegin());
        currentPage(true);
    }

    //改变字体
    public void changeTypeface(Typeface typeface) {
        this.typeface = typeface;
        mPaint.setTypeface(typeface);
        mBatterryPaint.setTypeface(typeface);
        calculateLineCount();
        measureMarginWidth();
        if (currentPage != null) {
            currentPage = getPageForBegin(currentPage.getBegin());
        }
        currentPage(true);
    }

    //改变背景
    public void changeBookBg(int type) {
        setBookBg(type);
        currentPage(false);
    }

    //设置页面的背景
    public void setBookBg(int type) {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        int color = 0;
        switch (type) {

            case Config.BOOK_BG_DEFAULT:
                canvas = null;
                bitmap.recycle();
                if (getBgBitmap() != null) {
                    getBgBitmap().recycle();
                }
                bitmap = BitmapUtil.decodeSampledBitmapFromResource(
                        mContext.getResources(), R.drawable.paper, mWidth, mHeight);
                color = mContext.getResources().getColor(R.color.read_font_default);
                setBookPageBg(mContext.getResources().getColor(R.color.read_bg_default));
                break;
            case Config.BOOK_BG_1:
                canvas.drawColor(mContext.getResources().getColor(R.color.read_bg_1));
                color = mContext.getResources().getColor(R.color.read_font_1);
                setBookPageBg(mContext.getResources().getColor(R.color.read_bg_1));
                break;
            case Config.BOOK_BG_2:
                canvas.drawColor(mContext.getResources().getColor(R.color.read_bg_2));
                color = mContext.getResources().getColor(R.color.read_font_2);
                setBookPageBg(mContext.getResources().getColor(R.color.read_bg_2));
                break;
            case Config.BOOK_BG_3:
                canvas.drawColor(mContext.getResources().getColor(R.color.read_bg_3));
                color = mContext.getResources().getColor(R.color.read_font_3);
                if (mBookPageWidget != null) {
                    mBookPageWidget.setBgColor(mContext.getResources().getColor(R.color.read_bg_3));
                }
                break;
            case Config.BOOK_BG_4:
                canvas.drawColor(mContext.getResources().getColor(R.color.read_bg_4));
                color = mContext.getResources().getColor(R.color.read_font_4);
                setBookPageBg(mContext.getResources().getColor(R.color.read_bg_4));
                break;
        }

        setBgBitmap(bitmap);
        //设置字体颜色
        setM_textColor(color);
    }

    public void setBookPageBg(int color) {
        if (mBookPageWidget != null) {
            mBookPageWidget.setBgColor(color);
        }
    }

    //设置日间或者夜间模式
    public void setDayOrNight(Boolean isNgiht) {
        initBg(isNgiht);
        currentPage(false);
    }

    public void clear() {
        currentCharter = 0;
        bookPath = "";
        bookName = "";
        bookList = null;
        mBookPageWidget = null;
        cancelPage = null;
        prePage = null;
        currentPage = null;
    }

    public static Status getStatus() {
        return mStatus;
    }

    //获取书本的章
    public List<BookCatalogue> getDirectoryList() {
        return mBookUtil.getDirectoryList();
    }

    //是否是第一页
    public boolean isfirstPage() {
        return m_isfirstPage;
    }

    //是否是最后一页
    public boolean islastPage() {
        return m_islastPage;
    }

    //设置页面背景
    public void setBgBitmap(Bitmap BG) {
        m_book_bg = BG;
    }

    //设置页面背景
    public Bitmap getBgBitmap() {
        return m_book_bg;
    }

    //设置文字颜色
    public void setM_textColor(int m_textColor) {
        this.m_textColor = m_textColor;
    }

    //获取文字颜色
    public int getTextColor() {
        return this.m_textColor;
    }

    //获取文字大小
    public float getFontSize() {
        return this.m_fontSize;
    }

    public void setPageWidget(PageWidget mBookPageWidget) {
        this.mBookPageWidget = mBookPageWidget;
    }

}
