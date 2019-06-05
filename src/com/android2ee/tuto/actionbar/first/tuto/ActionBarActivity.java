package com.android2ee.tuto.actionbar.first.tuto;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ActionBarActivity extends Activity {
	private static final String TAG = "ActionBarActivity";
	/**
	 * The action Bar
	 */
	private ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar);
		actionBar = getActionBar();
	}

	/******************************************************************************************/
	/** ActionBar Management **************************************************************************/
	/******************************************************************************************/
	/**
	 * Changing the title
	 */
	boolean toogleChangeTitle = true;
	String initialTitle = null;

	public void changeTitle(View view) {

		if (null == initialTitle) {
			initialTitle = (String) actionBar.getTitle();
		}
		// ensure changing the title only if it's displayed
		// either you'll get a bug
		if (!textVisible) {
			actionBar.setDisplayShowTitleEnabled(true);
		}
		if (toogleChangeTitle) {
			actionBar.setTitle("a new Title");
			actionBar.setSubtitle("A subtitle");
		} else {
			actionBar.setTitle(initialTitle);
			actionBar.setSubtitle(null);
		}
		toogleChangeTitle = !toogleChangeTitle;
		if (!textVisible) {
			actionBar.setDisplayShowTitleEnabled(false);

		}
	}

	/**
	 * Hiding the title
	 */
	boolean textVisible = true;

	public void hideText(View view) {
		if (textVisible) {
			actionBar.setDisplayShowTitleEnabled(false);
		} else {

			actionBar.setDisplayShowTitleEnabled(true);
		}
		textVisible = !textVisible;
	}

	/**
	 * The ClickListener of the button changeTitle
	 * 
	 * @param view
	 */
	boolean toogleIcon = false;

	@SuppressLint("NewApi")
	public void changeIcon(View view) {
		// Only available for IceScreamSandwich
		if (getResources().getBoolean(R.bool.postICS)) {
			if (toogleIcon) {
				Drawable drawable = getResources().getDrawable(R.drawable.first_ninepatch);
				actionBar.setDisplayUseLogoEnabled(true);
				actionBar.setLogo(drawable);
			} else {
				Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
				actionBar.setDisplayUseLogoEnabled(false);
				actionBar.setIcon(drawable);
			}
		}
		toogleIcon = !toogleIcon;
		
	}

	/**
	 * The ClickListener of the button changeTitle
	 * 
	 * @param view
	 */
	boolean iconVisible = true;

	public void hideIcon(View view) {
		if (iconVisible) {
			actionBar.setDisplayShowHomeEnabled(false);
		} else {
			actionBar.setDisplayShowHomeEnabled(true);
		}
		iconVisible = !iconVisible;
	}

	/**
	 * Changing the background
	 */
	int colorRed = 0xFFFF0000;
	int colorBlue = 0xFF00FF00;
	int colorGreen = 0xFF0000FF;
	int backgroundIndex = 0;

	public void changeBackground(View view) {
		ColorDrawable colorDrawable = new ColorDrawable();
		Drawable drawable = null;
		switch (backgroundIndex % 5) {
		case 0:
			colorDrawable.setColor(colorRed);
			break;
		case 1:
			colorDrawable.setColor(colorGreen);
			break;
		case 2:
			colorDrawable.setColor(colorBlue);
			break;
		case 3:
			drawable = getResources().getDrawable(R.drawable.list_item);
			break;
		case 4:
			drawable = getResources().getDrawable(R.drawable.first_ninepatch);
			break;
		}
		switch (backgroundIndex % 4) {
		case 0:
		case 1:
		case 2:
			actionBar.setBackgroundDrawable(colorDrawable);
			break;
		case 3:
		case 4:
			actionBar.setBackgroundDrawable(drawable);
			break;
		}
		backgroundIndex++;
		// workaround to take into account the background change
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		// ok, any way there is a bug when displaying a real picture
		// sometimes it works other times it doesn't (case 4)
		// to display a real picture you should not update dynamically, you should define it using
		// the style inheritance
	}

	/******************************************************************************************/
	/** SetHomeButtonEnable **************************************************************************/
	/******************************************************************************************/
	boolean homeEnable = false;

	@SuppressLint("NewApi")
	public void setHomeEnable(View view) {
		// Only available for IceScreamSandwich
		if (getResources().getBoolean(R.bool.postICS)) {
			if (homeEnable) {
				actionBar.setHomeButtonEnabled(false);
			} else {
				actionBar.setHomeButtonEnabled(true);
			}
			homeEnable = !homeEnable;
		}
	}

	boolean upEnable = false;

	@SuppressLint("NewApi")
	public void setUpEnable(View view) {
		// Only available for IceScreamSandwich
		if (getResources().getBoolean(R.bool.postICS)) {
			if (!upEnable) {
				actionBar.setDisplayUseLogoEnabled(false);
				actionBar.setDisplayHomeAsUpEnabled(true);
			} else {
				actionBar.setDisplayUseLogoEnabled(true);
				actionBar.setDisplayHomeAsUpEnabled(false);
			}
			upEnable = !upEnable;
		}
	}
	
	/******************************************************************************************/
	/** Change Activity to see if the actionBar's state is depending on the activity 
	 * or on the application And to see if the change persist
	 * And the Conclusion is they don't persist.
	 * So if you want to change ActionBar at runTime and to persist that state you need to
	 * Save those preferences into a SharedPreference
	 * Have a motherActivity for all your activities that updates the actionBar in the OnCreate
	 *  method using the values stored in the SharedPreference
	/******************************************************************************************/

	
	/**
	 * Change activity to see what happens to ActionBar
	 * 
	 * @param view
	 */
	public void changeActivty(View view) {
		Intent otherActivty =new Intent(this,OtherActivity.class);
		startActivity(otherActivty);
	}

	/******************************************************************************************/
	/** Menu **************************************************************************/
	/******************************************************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_action_bar, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case (android.R.id.home):
			Toast.makeText(this, "The home button has been clicked", Toast.LENGTH_SHORT).show();
			// should look like something like that:
//			Intent intent = new Intent(this, AppCompatActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
//			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}
}
