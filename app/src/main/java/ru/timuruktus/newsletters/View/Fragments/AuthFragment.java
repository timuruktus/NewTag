package ru.timuruktus.newsletters.View.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.IOException;

import ru.timuruktus.newsletters.Presenter.AuthPresenter;
import ru.timuruktus.newsletters.R;
import ru.timuruktus.newsletters.View.Fragments.Interface.IAuthFragmentCallBack;

public class AuthFragment extends Fragment implements IAuthFragmentCallBack,
                        View.OnClickListener{

    private AuthPresenter authPresenter;
    private TextView editTextEmail, editTextPass;
    private SliderLayout sliderShow;
    private Button regBut;
    private ProgressBar loadingBar;
    private TextInputLayout emailInput, passInput;
    private View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView =
                inflater.inflate(R.layout.auth_fragment, container, false);

        String custom_font = "fonts/Roboto-Italic.ttf";
        Typeface CF = Typeface.createFromAsset(getActivity().getAssets(), custom_font);

        loadingBar = (ProgressBar) rootView.findViewById(R.id.loadingBar);
        loadingBar.setVisibility(View.INVISIBLE);
        regBut = (Button) rootView.findViewById(R.id.regBut);
        regBut.setOnClickListener(this);
        regBut.setTypeface(CF);
        emailInput = (TextInputLayout) rootView.findViewById(R.id.emailInput);
        passInput = (TextInputLayout) rootView.findViewById(R.id.passInput);
        emailInput.setTypeface(CF);
        passInput.setTypeface(CF);
        editTextEmail = (TextView) rootView.findViewById(R.id.edit_text_email);
        editTextPass = (TextView) rootView.findViewById(R.id.edit_text_pass);
        editTextEmail.setTypeface(CF);
        editTextPass.setTypeface(CF);
        sliderShow = (SliderLayout) rootView.findViewById(R.id.slider);

        configureSliderImages();

        authPresenter = new AuthPresenter(this);
        return rootView;
    }

    /**
     * Called when fragment starts
     * !!DON'T CALL IT ANYWHERE ELSE!!
     */
    public void configureSliderImages(){
        TextSliderView textSliderView = new TextSliderView(getActivity());
        textSliderView.image(R.drawable.img1);
        sliderShow.addSlider(textSliderView);
        TextSliderView textSliderView2 = new TextSliderView(getActivity());
        textSliderView2.image(R.drawable.img2);
        sliderShow.addSlider(textSliderView2);
        TextSliderView textSliderView3 = new TextSliderView(getActivity());
        textSliderView3.image(R.drawable.img3);
        sliderShow.addSlider(textSliderView3);
        TextSliderView textSliderView4 = new TextSliderView(getActivity());
        textSliderView4.image(R.drawable.img4);
        sliderShow.addSlider(textSliderView4);
        sliderShow.startAutoCycle(10000,10000,true);
        sliderShow.setPresetTransformer("Stack"); //stack, tablet- good
    }


    /**
     * Changes a fragment
     * @param isItWasLogin - depends on what text will be shown in Toast
     *                     if "true" - you were signIn with login
     *                     if "false" - you were created your account
     */
    @Override
    public void changeFragment(boolean isItWasLogin) {
        if(isItWasLogin) {
            Log.d("tag", "changeFragment(itWasLogin == true)");
            Toast.makeText(getActivity().getApplicationContext(), R.string.auth_success_login,
                    Toast.LENGTH_SHORT).show();
        }else{
            Log.d("tag", "changeFragment(itWasLogin == false)");
            Toast.makeText(getActivity().getApplicationContext(), R.string.auth_success_singin,
                    Toast.LENGTH_SHORT).show();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, welcomeFragment);
    }

    @Override
    public void showRegError() {
        Context context = getActivity().getApplicationContext();
        Toast.makeText(context, R.string.auth_failed, Toast.LENGTH_SHORT).show();
        showLoadingBarCallBack(true);
    }

    /**
     * Shows start/end of loading
     * CALLBACK!
     * @param reverse - "true" if loading was stopped
     */
    @Override
    public void showLoadingBarCallBack(boolean reverse) {
        if(!reverse) {
            emailInput.setVisibility(View.INVISIBLE);
            passInput.setVisibility(View.INVISIBLE);
            regBut.setVisibility(View.INVISIBLE);
            loadingBar.setVisibility(View.VISIBLE);
        }else{
            emailInput.setVisibility(View.VISIBLE);
            passInput.setVisibility(View.VISIBLE);
            regBut.setVisibility(View.VISIBLE);
            loadingBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * CLICK LISTENER
     * @param v - View
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.regBut){
            authPresenter.onRegButClick(editTextEmail.getText().toString(), editTextPass.getText().toString());
        }
    }

    /**
     * Called, when Fragment Stopped
     */
    @Override
    public void onStop(){
        sliderShow.stopAutoCycle();
        super.onStop();
    }

}
