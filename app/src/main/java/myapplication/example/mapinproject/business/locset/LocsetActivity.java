package myapplication.example.mapinproject.business.locset;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class LocsetActivity extends FragmentActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CODE_LOCATION_SETTING = 2;
    private static final int REQUEST_CODE_GOOGLE_PLAY_SERVICES_ERROR = 3;

    //パーミッションの指定
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    static final String INTENT_EXTRA_KEY_PRIORITY = "intent.extra.key.priority";

    @Locset.SettingPriority
    private int mPriority = Locset.SettingPriority.HIGH_ACCURACY;

    private boolean mIsShowGooglePlayServiceErrorDialog = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPriority = getIntent().getIntExtra(INTENT_EXTRA_KEY_PRIORITY, Locset.SettingPriority.HIGH_ACCURACY);

        if (isPermissionGranted()) {
            // (6.0未満 or 既に許可済み)
            // 許可されているので端末の位置情報設定を行う
            requestLocationSetting();
            return;
        }
        // (6.0以上)
        // パーミッションをリクエスト
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_LOCATION_PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_LOCATION_SETTING: {
                if (resultCode == Activity.RESULT_OK) {
                    finishForResult(Locset.ResultCode.SUCCESS);
                } else {
                    finishForResult(Locset.ResultCode.LOCATION_SETTING_FAILURE);
                }
                break;
            }
            case REQUEST_CODE_GOOGLE_PLAY_SERVICES_ERROR: {
                finishForResult(Locset.ResultCode.GOOGLE_PLAY_SERVICES_UNAVAILABLE);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length != grantResults.length) {
            finishForResult(Locset.ResultCode.PERMISSION_FAILURE);
            return;
        }
        for (int i = 0; i < grantResults.length; i++) {
            // 拒否された
            //  1.まだ一度も要求していないパーミッションを渡した場合、falseが返る
            //  2.要求したパーミッションが、「今後は表示しない」というチェックを入れて拒絶された場合、falseが返る
            //  3.要求したパーミッションが拒絶されたことがあり、かつ今後表示しないチェックがつけられていない場合、trueが返る
            //  注）1と2が判別できないので注意
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    // 呼び出し元へ結果を返却（失敗）
                    finishForResult(Locset.ResultCode.PERMISSION_FAILURE);
                } else {
                    // 呼び出し元へ結果を返却（失敗）
                    finishForResult(Locset.ResultCode.PERMISSION_FAILURE_DO_NOT_ASK_AGAIN);
                }
                return;
            }
        }
        // 許可されたので端末の位置情報設定を行う
        requestLocationSetting();
    }

    private void finishForResult(@Locset.ResultCode int resultCode) {
        if (isFinishing()) {
            return;
        }
        setResult(resultCode);
        finish();
    }

    private boolean isPermissionGranted() {
        for (String permission : PERMISSIONS) {
            if (PermissionChecker.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestLocationSetting() {
        if (!checkGooglePlayServicesAvailable()) {
            return;
        }

        final LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .setAlwaysShow(true)
                .addLocationRequest(LocationRequest.create().setPriority(mPriority))
                .build();
        final Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this)
                .checkLocationSettings(request);

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    task.getResult(ApiException.class);
                    // 既に設定済み
                    finishForResult(Locset.ResultCode.SUCCESS);
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user a dialog.
                            try {
                                // ユーザに位置情報設定を変更してもらうためのダイアログを表示する
                                final ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(LocsetActivity.this, REQUEST_CODE_LOCATION_SETTING);
                                return;
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // 位置情報が取得できず、その状態からの復帰も難しい時呼ばれる
                            break;
                    }
                    finishForResult(Locset.ResultCode.LOCATION_SETTING_FAILURE);
                }
            }
        });
    }

    private boolean checkGooglePlayServicesAvailable() {
        final GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        final int result = googleApi.isGooglePlayServicesAvailable(getApplicationContext());
        if (result == ConnectionResult.SUCCESS) {
            return true;
        }
        if (googleApi.isUserResolvableError(result)) {
            if (googleApi.showErrorDialogFragment(this, result, REQUEST_CODE_GOOGLE_PLAY_SERVICES_ERROR)) {
                mIsShowGooglePlayServiceErrorDialog = true;
                getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                        if (mIsShowGooglePlayServiceErrorDialog && f instanceof DialogFragment) {
                            finishForResult(Locset.ResultCode.GOOGLE_PLAY_SERVICES_UNAVAILABLE);
                        }
                    }
                }, false);
                return false;
            }
        }
        finishForResult(Locset.ResultCode.GOOGLE_PLAY_SERVICES_UNAVAILABLE);
        return false;
    }
}