package com.odos.smartaqua.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PdfGeneratorNew {
    private static String TAG= PdfGeneratorNew.class.getSimpleName();
    private File mFile;
    private Context mContext;

    public PdfGeneratorNew(Context context) {
        this.mContext = context;
    }

    /*save image to pdf*/
    public void saveImageToPDF(View title, Bitmap bitmap) {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        if(!path.exists()) {
            path.mkdirs();
        }
        try {
            String fileName = ""+System.currentTimeMillis();
            mFile = new File(path + "/", fileName + ".pdf");
            if (!mFile.exists()) {
                int height = bitmap.getHeight();
                PdfDocument document = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), height, 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                title.draw(canvas);
                canvas.drawBitmap(bitmap, null, new Rect(0, bitmap.getHeight(), bitmap.getWidth(), bitmap.getHeight()), null);
                document.finishPage(page);
                try {
                    mFile.createNewFile();
                    OutputStream out = new FileOutputStream(mFile);
                    document.writeTo(out);
                    document.close();
                    out.close();
                    Log.e(TAG,"Pdf Saved at:"+mFile.getAbsolutePath());
                    Toast.makeText(mContext,"Pdf Saved at:"+mFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                    sharePDF(mContext,fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*method for generating bitmap from LinearLayout, RelativeLayout etc.*/
    public Bitmap getViewScreenShot(View view)
    {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        return bm;
    }


    private void sharePDF(Context context,String fileName){
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File outputFile = new File(path,fileName);
        Log.e("data--==",""+outputFile);
        Uri uri = Uri.fromFile(outputFile);
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setPackage("com.whatsapp");
        context.startActivity(share);
    }
    /*method for generating bitmap from ScrollView, NestedScrollView*/
    public Bitmap getScrollViewScreenShot(ScrollView nestedScrollView)
    {

        int totalHeight = nestedScrollView.getChildAt(0).getHeight();
        int totalWidth = nestedScrollView.getChildAt(0).getWidth();
        return getBitmapFromView(nestedScrollView,totalHeight,totalWidth);
    }


    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
}
