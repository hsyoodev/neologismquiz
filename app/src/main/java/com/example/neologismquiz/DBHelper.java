package com.example.neologismquiz;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbName = "quiz.db";
    private final Context context;

    public DBHelper(Context context) {
        super(context, dbName, null, 1);
        this.context = context;

        if (!isExistDB(context)) { // 앱 처음 실행 (DB 데이터 없음)
            copyDB(context); // DB 데이터 생성 (복사)
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        copyDB(context);
    }

    public boolean isExistDB(Context context) {
        String packageName = context.getPackageName();
        String filePath = "/data/data/" + packageName + "/databases/" + dbName;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public void copyDB(Context context) {
        AssetManager manager = context.getAssets();
        String packageName = context.getPackageName();
        String folderPath = "/data/data/" + packageName + "/databases";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        InputStream is = null;
        BufferedInputStream bis = null;
        OutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            is = manager.open(dbName);
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(folderPath + "/" + dbName);
            bos = new BufferedOutputStream(fos);
            int data = 0;
            while ((data = bis.read()) != -1) {
                bos.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public ArrayDeque<QuizDto> findByStageNumber(int stageNumber) {
        ArrayDeque<QuizDto> quizDtos = new ArrayDeque<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT question, answer, wrong FROM question WHERE stage=" + stageNumber + " ORDER BY RANDOM()", null);
        while (cursor.moveToNext()) {
            String question = cursor.getString(0);
            String answer = cursor.getString(1);
            String wrong = cursor.getString(2);
            QuizDto quizDto = new QuizDto(question, answer, wrong);
            quizDtos.offer(quizDto);
        }
        db.close();
        return quizDtos;
    }
}
