package lut.student.mybookshelf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    String[] names;
    String[] authors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        Intent in = getIntent();
        int index = in.getIntExtra("lut.student.ITEM_INDEX", -1);

        if (index > -1) {
            getDetails(index);

            int pic = getImage(index);
            ImageView img = (ImageView) findViewById(R.id.coverImageView);
            scaleImage(img, pic);

            // Example from: https://www.geeksforgeeks.org/android/how-to-save-switch-button-state-in-android/
            Switch readSwitch = (Switch) findViewById(R.id.readSwitch);
            SharedPreferences readPrefs = getSharedPreferences("save", MODE_PRIVATE);
            boolean savedValue = readPrefs.getBoolean("value" + index, false);
            readSwitch.setChecked(savedValue);
            readSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (readSwitch.isChecked()) {
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value" + index, true);
                        editor.apply();
                        readSwitch.setChecked(true);
                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value" + index, false);
                        editor.apply();
                        readSwitch.setChecked(false);
                    }
                }
            });
            // Example from: https://abhiandroid.com/ui/ratingbar#gsc.tab=0
            RatingBar bookRatingBar = (RatingBar) findViewById(R.id.bookRatingBar);
            SharedPreferences starPrefs = getSharedPreferences("save", MODE_PRIVATE);
            float rating = starPrefs.getFloat("stars" + index, 0.0f);
            bookRatingBar.setRating(rating);
            bookRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putFloat("stars" + index, rating);
                    editor.apply();

                }
            });
            //Example from: https://www.geeksforgeeks.org/android/radiogroup-in-android/
            RadioGroup genreRadioGroup = (RadioGroup) findViewById(R.id.genreRadioGroup);
            SharedPreferences genrePrefs = getSharedPreferences("save", MODE_PRIVATE);
            int genre = genrePrefs.getInt("genre" + index, 0);
            genreRadioGroup.check(genre);
            genreRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putInt("genre" + index, checkedId);
                    editor.apply();
                }
            });

            RadioGroup paceRadioGroup = (RadioGroup) findViewById(R.id.paceRadioGroup);
            SharedPreferences pacePrefs = getSharedPreferences("save", MODE_PRIVATE);
            int pace = genrePrefs.getInt("pace" + index, 0);
            paceRadioGroup.check(pace);
            paceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putInt("pace" + index, checkedId);
                    editor.apply();
                }
            });
            // Example from: https://stackoverflow.com/questions/15945285/how-can-i-uncheck-or-reset-the-radio-button
            Button clearButton = findViewById(R.id.clearButton);
            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    genreRadioGroup.clearCheck();
                    paceRadioGroup.clearCheck();

                }
            });

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private int getImage(int index) {
        switch (index) {
            case 0:
                return R.drawable.kettu;
            case 1:
                return R.drawable.gangsters;
            case 2:
                return R.drawable.housemaid;
            case 3:
                return R.drawable.lightsout;
            case 4:
                return R.drawable.idiots;
            default:
                return -1;
        }
    }

    private void getDetails(int index) {
        TextView bookTextView = (TextView) findViewById(R.id.bookTextView);
        TextView writerTextView = (TextView) findViewById(R.id.writerTextView);

        names = getResources().getStringArray(R.array.names);
        authors = getResources().getStringArray(R.array.authors);

        String book = names[index];
        String writer = authors[index];


        bookTextView.setText(book);
        writerTextView.setText(writer);

    }

    private void scaleImage(ImageView img, int pic) {
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);
    }
}