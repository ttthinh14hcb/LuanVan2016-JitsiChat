package org.jitsi.android.gui.account;

import org.jitsi.service.osgi.OSGiActivity;
//import org.jivesoftware.smack.*;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import nhom3.jitsi.*;

//Nhom 3
public class AccountRegisterActivity extends OSGiActivity {

	private EditText userIDField;
	private EditText passField;
	private EditText pass2Field;
	private EditText serverField;
	private EditText portField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.jabber_register);

		initEditTexts();
		initSignUpButton();
	}

	private void initEditTexts() {
		userIDField = (EditText) findViewById(R.id.jabber_reg_usernameField);
		passField = (EditText) findViewById(R.id.jabber_reg_passwordField);
		pass2Field = (EditText) findViewById(R.id.jabber_reg_passwordField2);
		serverField = (EditText) findViewById(R.id.jabber_reg_serverName);
		portField = (EditText) findViewById(R.id.jabber_reg_serverPort);
	}

	private void initSignUpButton() {
		final Button signInButton = (Button) findViewById(R.id.jabber_reg_btnSinUp);
		signInButton.setEnabled(true);

		signInButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String userID = userIDField.getText().toString();
				String password = passField.getText().toString();
				String password2 = pass2Field.getText().toString();
				String server = serverField.getText().toString();
				int port = 5222; // default port
				try {
					int tmp = Integer.parseInt(portField.getText().toString());
					if (tmp > 0)
						port = tmp;
				} catch (NumberFormatException e) {

				}

				if (password.equals(password2)) {
					// the two password fields are the same
					boolean result = createJabberAccount(server, port, userID,
							new String(password));

					if (result == true) {
						// return new NewAccount(
						// getCompleteUserID(userID, server), password,
						// server, String.valueOf(port));
					} else {

					}
				} else {
					// showErrorMessage(Resources
					// .getString("plugin.jabberaccregwizz.NOT_SAME_PASSWORD"));
				}

				// return null;

			}
		});
	}

	private boolean createJabberAccount(String server, int port,
			String username, String password) {
		/*
		 * try { ConnectionConfiguration config = new ConnectionConfiguration(
		 * server, port);
		 * 
		 * XMPPConnection xmppConnection = new XMPPConnection(config);
		 * xmppConnection.connect();
		 * 
		 * AccountManager accountManager = new AccountManager(xmppConnection);
		 * accountManager.createAccount(username, password); return true; }
		 * catch (XMPPException exc) { // logger.error(exc); if
		 * (exc.getXMPPError() != null && exc.getXMPPError().getCode() == 409) {
		 * // showErrorMessage(Resources //
		 * .getString("plugin.jabberaccregwizz.USER_EXISTS_ERROR"));
		 * 
		 * // logger.error("Error when created a new Jabber account :" // +
		 * " user already exist"); } else { //
		 * showErrorMessage(Resources.getResources().getI18NString( //
		 * "plugin.jabberaccregwizz.UNKNOWN_XMPP_ERROR", // new String[] {
		 * exc.getMessage() })); }
		 * 
		 * return false; }
		 */
		return false;
	}
}
