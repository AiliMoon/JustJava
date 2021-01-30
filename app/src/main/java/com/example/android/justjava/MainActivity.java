/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

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
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCream = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox chocolate = findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();

        String message = createOrderSummary(calculatePrice(hasChocolate, hasWhippedCream), hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.java_java_order_for) + name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if (quantity == 100) {

            String toast1 = getResources().getString(R.string.cannot_order_more_100);
            Toast.makeText(this, toast1, Toast.LENGTH_SHORT).show();

            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {

            String toast2 = getResources().getString(R.string.toast_less_1_coffee);
            Toast.makeText(this, toast2, Toast.LENGTH_SHORT).show();

            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method displays the given text on the screen.
     */

    private int calculatePrice(boolean addChocolate, boolean addWhippedCream) {
        int basePrice = 5;
        if (addChocolate) {
            basePrice += 1;
        }
        if (addWhippedCream) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(int price, boolean addWhippedCream, boolean chocolate, String name) {
        return getString(R.string.order_summary_name, name) +getString(R.string.add_whipped_cream, addWhippedCream) +getString(R.string.add_chocolate, chocolate) + getString(R.string.quantity_mainjava, quantity) + getString(R.string.total, NumberFormat.getCurrencyInstance().format(price)) + getString(R.string.thank_you);
    }
}