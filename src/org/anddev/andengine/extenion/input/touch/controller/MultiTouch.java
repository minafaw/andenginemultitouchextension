package org.anddev.andengine.extenion.input.touch.controller;

import java.lang.reflect.Method;

import org.anddev.andengine.util.SystemUtils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * @author Nicolas Gramlich
 * @since 16:00:38 - 14.07.2010
 */
public class MultiTouch {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private static Boolean SUPPORTED = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	public static boolean isSupported(final Context pContext) {
		if(SUPPORTED == null) {
			SUPPORTED = initSupported(pContext);
		} 

		return SUPPORTED;
	}

	private static boolean initSupported(final Context pContext) {
		try{
			final Method PackageManager_hasSystemFeatures = PackageManager.class.getMethod("hasSystemFeature",  new Class[]{String.class});
			final boolean supported = (PackageManager_hasSystemFeatures == null) ? false : (Boolean)PackageManager_hasSystemFeatures.invoke(pContext.getPackageManager(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
			return SystemUtils.isAndroidVersionOrHigher(Build.VERSION_CODES.ECLAIR_MR1) && supported;
		}catch(Throwable t) {
			return false;
		}
	}
	
	public static boolean isSupportedByAndroidVersion() {
		return SystemUtils.isAndroidVersionOrHigher(Build.VERSION_CODES.ECLAIR);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
