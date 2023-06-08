package moe.henry_zhr.telegram_no_custom_font;

import android.graphics.Typeface;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {
  @Override
  public void handleLoadPackage(LoadPackageParam lpparam) {
    // https://github.com/DrKLO/Telegram/blob/dfd74f809e97d1ecad9672fc7388cb0223a95dfc/TMessagesProj/src/main/java/org/telegram/messenger/AndroidUtilities.java#L1755-L1782
    XposedHelpers.findAndHookMethod("org.telegram.messenger.AndroidUtilities", lpparam.classLoader, "getTypeface", String.class, new XC_MethodHook() {
      @Override
      protected void beforeHookedMethod(MethodHookParam param) {
        // https://github.com/DrKLO/Telegram/tree/dfd74f809e97d1ecad9672fc7388cb0223a95dfc/TMessagesProj/src/main/assets/fonts
        // Ignored: Courier New, Merriweather
        switch ((String) param.args[0]) {
          case "fonts/rcondensedbold.ttf":
            param.setResult(Typeface.create("sans-serif-condensed", Typeface.BOLD));
            break;
          case "fonts/ritalic.ttf":
            param.setResult(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            break;
          case "fonts/rmedium.ttf":
            param.setResult(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            break;
          case "fonts/rmediumitalic.ttf":
            param.setResult(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));
            break;
          case "fonts/rmono.ttf":
            param.setResult(Typeface.MONOSPACE);
            break;
        }
      }
    });
  }
}
