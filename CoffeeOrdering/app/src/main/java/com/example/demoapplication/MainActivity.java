package com.example.demoapplication;
//import com.example.demoapplication.R ;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int basePrice = 10 ;
    int quantity= 2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameTextView = (EditText)findViewById(R.id.name_edittext);
        String nameEditText = nameTextView.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        if (hasWhippedCream){
            basePrice += 5 ;
        }

        CheckBox chocolateCheckbox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        if (hasChocolate){
            basePrice += 10 ;
        }

        int price = calaculatePrice();

        String priceMessage = createOrderSummary(nameEditText,hasWhippedCream,hasChocolate,price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+nameEditText);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

        displayMessage(priceMessage);

    }

    public String createOrderSummary (String name , boolean addWhippedCream, boolean addChocolate, int totalPrice ) {
        String priceMessage = " Name : "+name;
        priceMessage += "\n Add Whipped Cream ? "+addWhippedCream;
        priceMessage += "\n Add Chocolate ? "+addChocolate;
        priceMessage += "\n Quantity : "+ quantity ;
        priceMessage += "\n Total : $"+totalPrice ;
        priceMessage += "\n Thank you !";
        return priceMessage ;
    }

    public  int  calaculatePrice(){
        return (basePrice*quantity);
    }

    public  void  increment (View view ){
        if (quantity == 100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        display(++quantity);
    }

    public  void  decrement (View view ){
        if (quantity == 1){
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        display(--quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayMessage(String message){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
