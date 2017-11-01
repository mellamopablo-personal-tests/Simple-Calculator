package org.thinway.supercalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("SetTextI18n")
public class CalcActivity extends AppCompatActivity implements View.OnClickListener {

    // Widgets
    private TextView resultTextView;
    private Button addBtn;
    private Button subtractBtn;
    private Button multiplyBtn;
    private Button divBtn;
    private Button sevenBtn;
    private Button eightBtn;
    private Button nineBtn;
    private Button acBtn;
    private Button fourBtn;
    private Button fiveBtn;
    private Button sixBtn;
    private Button deleteBtn;
    private Button oneBtn;
    private Button twoBtn;
    private Button threeBtn;
    private Button pointBtn;
    private Button zeroBtn;
    private Button signBtn;
    private Button equalBtn;

    // Data
    private double mAccumulator;
    private char mOp;
    // Es true solo cuando el último botón pulsado ha sido un botón de operación
    // Justo cuando se pulse otro botón, esto se define como false (a menos, claro,
    // que ese úlitmo botón sea otro botón de operación)
    private boolean justPressedOperationButton;
    // Contiene el operador de la última operación, para saber qué operación aplicar
    // si el usuario presiona igual de nuevo
    private char lastOperationOperator;
    // El segundo operando de la última operación
    private double lastOperationSecondNumber;
    // Si se debería repetir la última operación. Cuando se pulsa igual, esto es true. Después
    // cambia a false.
    private boolean shouldRepeatLastOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcalc);

        connectWidgets();
        connectEventsToWidgets();

        initializeValues();
    }

    /**
     * Initialize all non-widget members attributes.
     */
    private void initializeValues() {
        mAccumulator = 0;
        mOp = 0;
        justPressedOperationButton = false;
        shouldRepeatLastOperation = false;
        resultTextView.setText("0");
    }

    /**
     * Set OnClickListener on all interface button.
     */
    private void connectEventsToWidgets() {
        addBtn.setOnClickListener(this);
        subtractBtn.setOnClickListener(this);
        multiplyBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        sevenBtn.setOnClickListener(this);
        eightBtn.setOnClickListener(this);
        nineBtn.setOnClickListener(this);
        acBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        fiveBtn.setOnClickListener(this);
        sixBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        oneBtn.setOnClickListener(this);
        twoBtn.setOnClickListener(this);
        threeBtn.setOnClickListener(this);
        pointBtn.setOnClickListener(this);
        zeroBtn.setOnClickListener(this);
        signBtn.setOnClickListener(this);
        equalBtn.setOnClickListener(this);
    }

    /**
     * Connect all the widgets to their correspondent element of the interface
     */
    private void connectWidgets() {
        resultTextView = (TextView) findViewById(R.id.text_view_result);
        addBtn = (Button) findViewById(R.id.btn_add);
        subtractBtn = (Button) findViewById(R.id.btn_subtract);
        multiplyBtn = (Button) findViewById(R.id.btn_multiply);
        divBtn = (Button) findViewById(R.id.btn_div);
        sevenBtn = (Button) findViewById(R.id.btn_seven);
        eightBtn = (Button) findViewById(R.id.btn_eight);
        nineBtn = (Button) findViewById(R.id.btn_nine);
        acBtn = (Button) findViewById(R.id.btn_ac);
        fourBtn = (Button) findViewById(R.id.btn_four);
        fiveBtn = (Button) findViewById(R.id.btn_five);
        sixBtn = (Button) findViewById(R.id.btn_six);
        deleteBtn = (Button) findViewById(R.id.btn_delete);
        oneBtn = (Button) findViewById(R.id.btn_one);
        twoBtn = (Button) findViewById(R.id.btn_two);
        threeBtn = (Button) findViewById(R.id.btn_three);
        pointBtn = (Button) findViewById(R.id.btn_point);
        zeroBtn = (Button) findViewById(R.id.btn_zero);
        signBtn = (Button) findViewById(R.id.btn_sign);
        equalBtn = (Button) findViewById(R.id.btn_equal);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        boolean definingSecondNumber = justPressedOperationButton;
        justPressedOperationButton = false;

        Button button = (Button) v;
        switch (v.getId()) {
            case R.id.btn_zero:
            case R.id.btn_one:
            case R.id.btn_two:
            case R.id.btn_three:
            case R.id.btn_four:
            case R.id.btn_five:
            case R.id.btn_six:
            case R.id.btn_seven:
            case R.id.btn_eight:
            case R.id.btn_nine:
                readNumber(button, definingSecondNumber);
                break;
            case R.id.btn_add:
            case R.id.btn_subtract:
            case R.id.btn_multiply:
            case R.id.btn_div:
                applyOp(button);
                break;
            case R.id.btn_ac:
                initializeValues();
                break;
            case R.id.btn_sign:
                changeSign();
                break;
            case R.id.btn_point:
                addPoint();
                break;
            case R.id.btn_delete:
                deleteNumber();
                break;
            case R.id.btn_equal:
                makeOperation();
                break;
            default:
                // Error
        }

        shouldRepeatLastOperation = v.getId() == R.id.btn_equal;
    }

    /**
     * Apply the selected operation
     */
    private void makeOperation() {
        if (shouldRepeatLastOperation) {

            double currentNumber = Double.parseDouble(resultTextView.getText().toString());
            double result = 0;

            switch (lastOperationOperator) {
                case '+':
                    result = currentNumber + lastOperationSecondNumber;
                    break;
                case '-':
                    result = currentNumber - lastOperationSecondNumber;
                    break;
                case '*':
                    result = currentNumber * lastOperationSecondNumber;
                    break;
                case '/':
                    result = currentNumber / lastOperationSecondNumber;
                    break;
                default:
            }

            long totalInt = (long) result;

            if (result == (double) totalInt) {
                resultTextView.setText(totalInt + "");
            } else {
                resultTextView.setText(result + "");
            }

        } else {

            double secondNumber = Double.parseDouble(resultTextView.getText().toString());
            double total = 0;

            switch (mOp) {
                case '+':
                    total = mAccumulator + secondNumber;
                    break;
                case '-':
                    total = mAccumulator - secondNumber;
                    break;
                case '*':
                    total = mAccumulator * secondNumber;
                    break;
                case '/':
                    total = mAccumulator / secondNumber;
                    break;
                default:
            }

            long totalInt = (long) total;

            if (total == (double) totalInt) {
                resultTextView.setText(totalInt + "");
            } else {
                resultTextView.setText(total + "");
            }

            lastOperationOperator = mOp;
            lastOperationSecondNumber = secondNumber;

        }
    }

    private void applyOp(Button button) {
        mOp = button.getText().toString().charAt(0);
        mAccumulator = Double.parseDouble(resultTextView.getText().toString());
        justPressedOperationButton = true;
    }

    private void deleteNumber() {
        String actualNumber = resultTextView.getText().toString();
        int parsedNumber;

        try {
            parsedNumber = Integer.parseInt(actualNumber);
        } catch (NumberFormatException e) {
            parsedNumber = 0;
        }

        if (actualNumber.length() == 1 || actualNumber.length() == 2 && parsedNumber < 0) {
            resultTextView.setText("0");
        } else {
            resultTextView.setText(
                    actualNumber.substring(0, actualNumber.length() - 1)
            );
        }
    }

    private void addPoint() {
        if (resultTextView.getText().toString().indexOf('.') == -1) {
            resultTextView.setText(
                    resultTextView.getText().toString() + '.'
            );
        }
    }

    private void changeSign() {
        String actualNumber = resultTextView.getText().toString();

        if (!actualNumber.equals("0")) {
            if (actualNumber.charAt(0) == '-') {
                actualNumber = actualNumber.substring(1);
            } else {
                actualNumber = '-' + actualNumber;
            }
            resultTextView.setText(actualNumber);
        }
    }

    /**
     * @param button El botón pulsado
     * @param definingSecondNumber True si se está definiendo el segundo número de una operación
     */
    private void readNumber(Button button, boolean definingSecondNumber) {
        String digit = button.getText().toString();
        String actualNumber = resultTextView.getText().toString();

        Log.d("CalcActivity", "mAccumulator = " + mAccumulator + " | mOp = " + mOp);

        if (actualNumber.equals("0") || definingSecondNumber) {
            resultTextView.setText(digit);
        } else {
            resultTextView.setText(
                    resultTextView.getText().toString() + digit
            );
        }

        if (digit.equals("0") && mOp == '/') {
            equalBtn.setEnabled(false);
        } else {
            equalBtn.setEnabled(true);
        }


    }
}
