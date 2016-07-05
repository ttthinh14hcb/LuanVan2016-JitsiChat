package nhom3.jitsi;

import java.util.*;

import net.java.sip.communicator.service.callhistory.*;
import net.java.sip.communicator.service.metahistory.*;

import org.jitsi.android.JitsiApplication;
import org.jitsi.android.gui.AndroidGUIActivator;
import org.jitsi.android.gui.util.AndroidCallUtil;
import org.jitsi.service.osgi.OSGiActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.*;

public class CallHistoryActivity extends OSGiActivity {
	private List<Object> historyList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_callhistory);
		ListView list = (ListView) findViewById(R.id.callHListView);
		CallHistoryAdapter adapter;

		try {

			MetaHistoryService metaHistory = AndroidGUIActivator
					.getMetaHistoryService();

			if (metaHistory == null)
				return;
			String[] chatHistoryFilter = new String[] { CallHistoryService.class
					.getName() };

			historyList = (List<Object>) metaHistory.findLast(
					chatHistoryFilter, null, 50);
			Collections.reverse(historyList);

		} catch (Exception e) {
			e.getMessage();
		}

		if (historyList != null) {
			adapter = new CallHistoryAdapter(this, historyList);
			list.setAdapter(adapter);

			list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					CallRecord rc = (CallRecord) historyList.get(position);
					if (rc.getPeerRecords().size() > 0) {
						String addr = rc.getPeerRecords().get(0)
								.getPeerAddress();
						if (addr.contains("/")) {
							addr = addr.substring(0, addr.indexOf('/'));
						}
						AndroidCallUtil.createAndroidCall(
								JitsiApplication.getGlobalContext(), null, addr);
					}
				}
			});
		}
	}
}
