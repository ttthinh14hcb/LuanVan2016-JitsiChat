package nhom3.jitsi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import net.java.sip.communicator.service.callhistory.CallPeerRecord;
import net.java.sip.communicator.service.callhistory.CallRecord;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CallHistoryAdapter extends BaseAdapter {

	private Activity activity;
	private List<Object> data;
	private static LayoutInflater inflater = null;

	// public ImageLoader imageLoader;

	public CallHistoryAdapter(Activity a, List<Object> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// imageLoader=new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "SimpleDateFormat", "InflateParams" })
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.row_callhistory, null);
		DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

		TextView name = (TextView) vi.findViewById(R.id.r_callHistoryName); // title
		TextView address = (TextView) vi
				.findViewById(R.id.r_callHistoryAddress); // artist name
		TextView time = (TextView) vi.findViewById(R.id.r_callHistoryTime); // duration
		ImageView thumb_image = (ImageView) vi
				.findViewById(R.id.r_callHistoryIcon); // thumb image

		CallRecord rc = ((CallRecord) data.toArray()[position]);

		String sDic = rc.getDirection();
		String sName = "";
		String sAddress = "";

		String sTime = dateFormat1.format(rc.getStartTime());

		for (CallPeerRecord pc : rc.getPeerRecords()) {
			sName += pc.getDisplayName() + " ";
			sAddress += pc.getPeerAddress() + " ";

		}

		name.setText(sName);
		address.setText(sAddress);
		time.setText(sTime);

		int res = R.drawable.arrow_up;
		if (sDic.equals("in")) {
			if (dateFormat2.format(rc.getStartTime()).equals(dateFormat2.format(rc.getEndTime()))) {
				res = R.drawable.call_miss;
			} else {
				res = R.drawable.call_in;
			}
		} else if (sDic.equals("out")) {
			res = R.drawable.call_out;
		} else {
			res = R.drawable.call_miss;
		}
		thumb_image.setImageResource(res);
		// thumb_image.setBackgroundResource(res);
		thumb_image.setTag(res);

		return vi;
	}
}
