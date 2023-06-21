package com.odos.smartaqua.utils;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import com.odos.smartaqua.BuildConfig;
import com.odos.smartaqua.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.List;

public class PdfGeneratorNew {
    private static String TAG = PdfGeneratorNew.class.getSimpleName();
    private File mFile;
    private Context mContext;

    public PdfGeneratorNew(Context context) {
        this.mContext = context;
    }

    /*save image to pdf*/
    public void saveImageToPDF(View title, Bitmap bitmap) {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        if (!path.exists()) {
            path.mkdirs();
        }
        try {
            String fileName = "" + System.currentTimeMillis();
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
                    sharePDF(mContext, mFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*method for generating bitmap from LinearLayout, RelativeLayout etc.*/
    public Bitmap getViewScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        return bm;
    }


    private void sharePDF(Context context, String fileName) {
        File outputFile = new File(fileName);
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", outputFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent chooser = Intent.createChooser(shareIntent, context.getResources().getText(R.string.app_name));
            context.startActivity(chooser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*method for generating bitmap from ScrollView, NestedScrollView*/
    public Bitmap getScrollViewScreenShot(ScrollView nestedScrollView) {

        int totalHeight = nestedScrollView.getChildAt(0).getHeight();
        int totalWidth = nestedScrollView.getChildAt(0).getWidth();
        return getBitmapFromView(nestedScrollView, totalHeight, totalWidth);
    }


    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
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
