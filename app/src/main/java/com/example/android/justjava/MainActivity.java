

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import com.example.android.justjava.R;

import static android.R.id.message;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method increments the quqntity by 1 each time it is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }


    /**
     * This method decrements the quantity by 1 each time it is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }



    /**
     * This method displays the given quantity value on the screen.
     */



    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        int whippedCream = 0;
        int whipChoco = 0;


        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.check_box);
        boolean wantWhippedCream = hasWhippedCream.isChecked();
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.check_box1);
        boolean wantChocolate = hasChocolate.isChecked();
        EditText name = (EditText) findViewById(R.id.enter_name);
        Editable yourName = name.getText();
        if(wantWhippedCream == true){
            whippedCream = 1;
        }
        if(wantChocolate == true){
            whipChoco = 1;
        }



        int a = calculatePrice(quantity, whipChoco, whippedCream);
        String str = createOrderSummary(a, quantity, wantWhippedCream, wantChocolate, yourName);
        composeEmail(str);

    }


    public void composeEmail(String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java coffee ordering");
        intent.putExtra(Intent.EXTRA_TEXT, subject);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void display(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView.setText("" + number);
    }










    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, int Choco, int Cream) {
        int price = quantity * (5 + 1 * Cream + 2 * Choco);
        return price;
    }

    private String createOrderSummary(int price, int quantity, boolean addWhippedCream, boolean addChocolate, Editable cusName){

        String priceMessage = getString(R.string.order_name, cusName);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_add_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }



}