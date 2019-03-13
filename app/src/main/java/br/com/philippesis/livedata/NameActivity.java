package br.com.philippesis.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView helloWorld;

    private Button btnChange;

    private NameViewModel model;

    private int count = 0;

    private String anotherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Other code to setup the activity...
        setupUI();
        setupListeners();

        // Get the ViewModel.
        model = ViewModelProviders.of(this).get(NameViewModel.class);

        // Create the oserver whith updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // Update the UI, in this case, a TextView
                helloWorld.setText(s);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getCurrentName().observe(this, nameObserver);
    }

    private void setupUI() {
        helloWorld = findViewById(R.id.idHelloWorld);
        btnChange = findViewById(R.id.idbtnChange);
    }

    private void setupListeners() {
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnChange) {
            switch (count) {
                case 0:
                    anotherName = "How are you?";
                    count ++;
                    break;
                case 1:
                    anotherName = "Fine, thanks. :-)";
                    count ++;
                    break;
                    default:
                        anotherName = "Hello World again!";
                        count = 0;
                        break;
            }
            model.getCurrentName().setValue(anotherName);
        }
    }
}
