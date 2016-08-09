package org.jitsi.android.gui.call;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.os.IBinder;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.widget.Toast;
import android.util.Log;


public class RecordService extends Service implements
		MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener {

	private MediaRecorder recorder = null;
	private File recording = null;;

	public void onCreate() {
		super.onCreate();
		recorder = new MediaRecorder();
		Log.i("CallRecorder", "onCreate created MediaRecorder object");
	}

	@SuppressLint("SimpleDateFormat") 
	public void onStart(Intent intent, int startId) {

		try {
			String currentDateTimeString = new SimpleDateFormat(
					"dd-MM-yyyy hh-mm-ss").format(new Date());
			String mFileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			mFileName += "/" + currentDateTimeString + ".3gp";

			recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			recorder.setOutputFile(mFileName);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			recorder.setOnInfoListener(this);
			recorder.setOnErrorListener(this);

			try {
				recorder.prepare();
			} catch (java.io.IOException e) {
				Log.e("CallRecorder",
						"RecordService::onStart() IOException attempting recorder.prepare()\n");
				Toast t = Toast.makeText(getApplicationContext(),
						"CallRecorder was unable to start recording: " + e,
						Toast.LENGTH_LONG);
				t.show();
				recorder = null;
				return;
			}
			Log.d("CallRecorder", "recorder.prepare() returned");

			recorder.start();
			Log.i("CallRecorder", "recorder.start() returned");
		} catch (java.lang.Exception e) {
			Toast t = Toast.makeText(getApplicationContext(),
					"CallRecorder was unable to start recording: " + e,
					Toast.LENGTH_LONG);
			t.show();

			Log.e("CallRecorder",
					"RecordService::onStart caught unexpected exception", e);
			recorder = null;
		}

		return;
	}

	public void onDestroy() {
		super.onDestroy();

		if (null != recorder) {
			Log.i("CallRecorder",
					"RecordService::onDestroy calling recorder.release()");
			recorder.release();
			Toast t = Toast.makeText(getApplicationContext(),
					"CallRecorder finished recording call to " + recording,
					Toast.LENGTH_LONG);
			t.show();
		}
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public boolean onUnbind(Intent intent) {
		return false;
	}

	public void onRebind(Intent intent) {
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		Log.e("CallRecorder", "RecordService got MediaRecorder onError callback with what: " + what + " extra: " + extra);
        mr.release();
	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		// TODO Auto-generated method stub
		
	}

	
}