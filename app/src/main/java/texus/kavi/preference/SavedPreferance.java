package texus.kavi.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author Sandeep Kumar
 *
 */
public class SavedPreferance {
	
	private static final String READ_FROM_ASSET = "readFromAsset";
	private static final String VERSION = "version";
	

	public static void setReadFromAsset(Context context, boolean status) {
		Editor edit = getPreferance(context).edit();
		edit.putBoolean(READ_FROM_ASSET, status);
		edit.commit();
	}
	public static boolean getReadFromAsset(Context context) {
		return getPreferance(context).getBoolean(READ_FROM_ASSET, false);
	}

	public static void setVersion(Context context, int  update_count) {
		Editor edit = getPreferance(context).edit();
		edit.putInt(VERSION, update_count);
		edit.commit();
	}

	public static int getVersion(Context context) {
		return getPreferance(context).getInt(VERSION, 0);
	}
	
	private static SharedPreferences getPreferance(Context context) {
		SharedPreferences preferences = android.preference.PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences;
	}
}
