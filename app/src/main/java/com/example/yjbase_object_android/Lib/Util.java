package com.example.yjbase_object_android.Lib;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;

public class Util {

    /* Object -> Int로 파싱 진행해주며, 파싱 진행 불가 시 0 return */
    public static int parseInt (Object srcData) {
        int retVal = 0;

        try {
            retVal = Integer.parseInt ((String) srcData);
        }

        catch (Exception e) {}
        return (retVal);
    }

    /* Int형 값을 무조건 올림 ( 개념이 익숙하지 않음 ) */
    public static int forceUp (int v, int k)
    {
        int mod = v % k;

        if (mod > 0)
        {
            v = v - mod + k;
        }

        return v;
    }

    /* Token성 값 생성 */
    public static String generateToken() {
        String token = null;

        try {
            SecureRandom secureRandom = SecureRandom.getInstance ("SHA1PRNG");
            MessageDigest digest = MessageDigest.getInstance ("SHA-256");
            secureRandom.setSeed (secureRandom.generateSeed (128));
            token = new String (digest.digest ((secureRandom.nextLong () + "").getBytes ()));
        }
        catch (NoSuchAlgorithmException e) { e.printStackTrace (); }
        return token;
    }

    /* length수 만큼 SALTCHARS에서 radom으로 뽑아서 return */
    public static String randomString(int lenth){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < lenth) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString();
    }

    /* fontSize 크기에 맞게 width 사이즈 return */
    public static int getWidth(Context context, int maxSize, float fontSize){

        TextView tv = new TextView(context);
        tv.setTextSize(fontSize);

        String text = "";
        while (maxSize != 0){
            text += "가";
            maxSize--;
        }
        return (int) tv.getPaint().measureText(text);
    }

    /**
     * listDialog 만들 떄 height설정을 위해 사용되는 함수
     * MeasureSpec : 위젯 크기를 결정하는 메소드 AT_MOST : 자식 adapter가 가질 수 있는 최대크기
     * */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int cnt = listAdapter.getCount();
        if (cnt > 10) cnt = 10;

        for (int i = 0; i < cnt; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);   // UNSPECIFIED : 크기 제한 없음
            totalHeight += listItem.getMeasuredHeight(); // listItem View height return

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams(); // ViewGroup.LayoutParams : 현재 설정되어 있는 레이아웃 파라미터 조사
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
