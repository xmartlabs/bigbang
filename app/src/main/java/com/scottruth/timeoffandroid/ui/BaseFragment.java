package com.scottruth.timeoffandroid.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.TimeOffApplication;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by santiago on 15/09/15.
 */
public abstract class BaseFragment extends RxFragment {
    @Nullable
    private ProgressDialog progressDialog;

    @LayoutRes
    protected abstract int getLayoutResId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentArgs.inject(this);

        TimeOffApplication.getContext().inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void showAlertError(int stringResId) {
        new AlertDialog.Builder(getContext())
                .setMessage(stringResId)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

    public void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    protected void removeItselfFromActivity() {
        ((BaseAppCompatActivity) getActivity()).removeFragment(this);
    }

    protected void removeItselfFromParentFragment() {
        getParentFragment().getChildFragmentManager().beginTransaction().remove(this).commit();
    }

    protected void removeItselfFromParent() {
        if (getParentFragment() == null) {
            removeItselfFromActivity();
        } else {
            removeItselfFromParentFragment();
        }
    }

//    // TODO: use next if fragments need show to show a tutorial
//    public void showTutorialIfNeeded() {
//
//    }
//
//    protected boolean showTutorialIfNeeded(int titleResId, int textResId, @NonNull View view, @NonNull String onceTag) {
//        if (Once.beenDone(Once.THIS_APP_INSTALL, onceTag) || showcaseView != null) {
//            return false;
//        } else {
//            showcaseView = new ShowcaseView.Builder(getActivity())
//                    .blockAllTouches()
//                    .hideOnTouchOutside()
//                    .replaceEndButton(R.layout.showcase_button)
//                    .setContentText(textResId)
//                    .setContentTitle(titleResId)
//                    .setOnClickListener(v -> {
//                        showcaseView.hide();
//                        Once.markDone(onceTag);
//                        showcaseView = null;
//                        showTutorialIfNeeded();
//                    })
//                    .setStyle(R.style.Tutorial)
//                    .setTarget(new ViewTarget(view))
//                    .withMaterialShowcase()
//                    .build();
//
//            showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
//            showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
//
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
//            int marginBottom = (int) getResources().getDimension(R.dimen.fab_margin_button);
//            int marginStart = MetricsHelper.dpToPxInt(16, getActivity());
//            layoutParams.setMargins(marginStart, 0, 0, marginBottom);
//            showcaseView.setButtonPosition(layoutParams);
//
//            return true;
//        }
//    }
}
