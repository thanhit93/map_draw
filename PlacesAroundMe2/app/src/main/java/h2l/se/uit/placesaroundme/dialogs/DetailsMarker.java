package h2l.se.uit.placesaroundme.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import h2l.se.uit.placesaroundme.R;

/**
 * Created by PCPV on 11/07/2016.
 */
@EFragment(R.layout.dialog_details_marker)
public class DetailsMarker extends android.support.v4.app.DialogFragment {
    @ViewById(R.id.dialog_details_marker_ll_raw)
    LinearLayout llRaw;

    public static DetailsMarker newInstance(CallBackRaw callBackRaw) {
        DetailsMarker dialog = DetailsMarker_.builder().build();
        dialog.callBackRaw=callBackRaw;
        return dialog;
    }
    @AfterViews
    void initView() {
        getDialog().getWindow().setGravity(Gravity.CENTER);

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Click(R.id.dialog_details_marker_ll_raw)
    void clickedLlRaw(){
     if(callBackRaw!=null){
         callBackRaw.finish();
         dismiss();
     }
    }
    @Click(R.id.dialog_details_marker_ll_dismiss)
    void clickedDismiss(){
        dismiss();
    }
    private CallBackRaw callBackRaw;
    public interface CallBackRaw{
        void finish();
    }

}
