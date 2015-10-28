package surfaceviewtest.charlie.com.surfaceviewtest;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

/**
 * 利用隐式意图(intent)使用手机录像录制
 */
public class IntentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
    }

    public void recordVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        // 4. 设置录音要保存的位置
        File recorderFold = FileUtils.getMediaRecorderFolder(this);
        File targetFile = new File(recorderFold, "intent-video-" + System.currentTimeMillis() + ".mp4");

        Uri uri = Uri.fromFile(targetFile);

        // set the video file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        // set the video quality high
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 113);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 113){
            if (data != null) {
                Toast.makeText(this, "录制完成" + data.getData(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}





