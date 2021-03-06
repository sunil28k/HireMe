package hire.omnify.com.hireme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import hire.omnify.com.hireme.service.SortingService;
import hire.omnify.com.hireme.ui.TwentyRandomNumbersFragment;

public class MainActivity extends AppCompatActivity {

    private Random randomGenerator;
    private int randomNumber;
    private final int min = 10;
    private final int max = 99;
    int randomNumArray[] = new int[20];
    private int idx;
    StringBuilder stringOfRandomNo = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwentyRandomNumbersFragment twentyRandomNumbersFragment = new TwentyRandomNumbersFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.random_num_container,twentyRandomNumbersFragment)
                .commit();

        new AsyncCaller().execute(new Integer(randomNumber));

    }

    public void sortRandomNumber(View view){


        Intent intent = new Intent(getApplicationContext(), SortingService.class);
        intent.putExtra("arrayOfRandomNum", randomNumArray);
        startService(intent);

    }

    public void generateRandomNumber(View view){

        TextView textViewRandomNum = (TextView)findViewById(R.id.text_view_random_number);

        stringOfRandomNo.setLength(0);


        randomGenerator = new Random();

        for (idx = 1; idx <= 20; ++idx){

            showRandomInteger(min, max, randomGenerator);
        }

        textViewRandomNum.setText(stringOfRandomNo.toString());
    }


    private void showRandomInteger(int lMin, int lMax, Random lRandom){


        if (lMin > lMax) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }

        int range = lMax - lMin + 1;

        int fraction = (int)(range * lRandom.nextDouble());
        randomNumber = (fraction + lMin);




        stringOfRandomNo.append(Integer.toString(randomNumber)+ " ");


    }

    @Override
    protected void onResume() {

        super.onResume();

        new AsyncCaller().execute(new Integer(randomNumber));

    }


    private class AsyncCaller extends AsyncTask<Integer, Void, Void>
    {

        @Override
        protected Void doInBackground(Integer... integers) {

            int randomNum = integers[0].intValue();
            randomNumArray[idx]= randomNum;
            return null;
        }
    }


}


