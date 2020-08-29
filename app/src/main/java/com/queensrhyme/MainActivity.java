package com.queensrhyme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editTextRhyme, editTextChefCount;
    TextView textViewSafePosition;
    Button buttonGetPosition;
    int rhymeWordCount =0, numberOfChefs =0;
    String rhyme;
    int probableSafePosition = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Views
        editTextRhyme = findViewById(R.id.editTextRhyme);
        editTextChefCount = findViewById(R.id.editTextChefCount);
        textViewSafePosition = findViewById(R.id.textViewSafePosition);
        buttonGetPosition = findViewById(R.id.buttonGetPosition);

        //getting Rhyme from the editText
        buttonGetPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextChefCount.getText().toString().isEmpty()){
                    editTextRhyme.setError("Please enter the rhyme");
                } else {
                    editTextRhyme.setError(null);
                    rhyme = editTextRhyme.getText().toString().trim();
                    rhymeWordCount = wordCount(rhyme);
                }

                if (editTextChefCount.getText().toString().isEmpty()){
                    editTextChefCount.setError("Please enter the number of chefs");
                } else {
                    editTextChefCount.setError(null);
                    numberOfChefs = Integer.parseInt(editTextChefCount.getText().toString().trim());
                }
                probableSafePosition = safePosition(numberOfChefs, rhymeWordCount);
                textViewSafePosition.setVisibility(View.VISIBLE);
                textViewSafePosition.setText("Probable Safe Position is: " + probableSafePosition);
            }
        });



    }

    //Method to return number of words in Rhyme
    static int wordCount(String rhyme)
    {
        int count=0;
        char[] ch = new char[rhyme.length()];
        for(int i=0;i<rhyme.length();i++)
        {
            ch[i]= rhyme.charAt(i);
            if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
                count++;
        }
        return count;
    }

    //Method To get safe position
    static int safePosition(int chefCount, int wordCount){
        int position = 0;
        if(chefCount == 1) {
            return 1;
        }
        if(chefCount == 2 ) {
            if(wordCount % 2 == 0) {
                return 1;
            } else {
                return 2;
            }
        } else {
            int sum = 0;
            //Sum is the position of chef removed in each iteration
            for(int i = 3; i <= chefCount; i++)
            {
                sum = (sum + wordCount) % i;
            }
            return sum +1; // sum is added with one as our position starts with 1
        }
    }
}
