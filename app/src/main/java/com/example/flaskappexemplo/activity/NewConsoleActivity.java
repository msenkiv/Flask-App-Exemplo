package com.example.flaskappexemplo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.flaskappexemplo.R;
import com.example.flaskappexemplo.model.Console;
import com.example.flaskappexemplo.util.APISingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class NewConsoleActivity extends AppCompatActivity {

    private EditText editName, editYear, editPrice,editQuantity;
    private Switch editActive;
    private long id;
    private Console console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_console);

        editName = findViewById(R.id.editName);
        editYear = findViewById(R.id.editYear);
        editPrice = findViewById(R.id.editPrice);
        editActive = findViewById(R.id.editActive);
        editQuantity = findViewById(R.id.editQuantity);

        id = getIntent().getLongExtra("ID",0);

        if(id != 0){
            loadConsole();
        }
    }

    private void loadConsole() {
        String url = "http://10.0.2.2:5000/api/console/"+id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                console = new Console();
                try {
                    console.setId(response.getLong("id"));
                    console.setName(response.getString("name"));
                    console.setYear(response.getInt("year"));
                    console.setPrice(response.getDouble("price"));
                    console.setActive(response.getBoolean("active"));
                    console.setQuantity(response.getInt("quantity"));


                    editName.setText(console.getName());
                    editYear.setText(String.valueOf(console.getYear()));
                    editPrice.setText(String.valueOf(console.getPrice()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void createConsole(String url, final int method){
        JSONObject object = new JSONObject();

        try {
            object.put("name",editName.getText().toString());
            object.put("year",Integer.parseInt(editYear.getText().toString()));
            object.put("price",Double.parseDouble(editPrice.getText().toString()));
            object.put("active",Boolean.parseBoolean(editActive.getText().toString()));
            object.put("quantity",Integer.parseInt(editPrice.getText().toString()));

            JsonObjectRequest request = new JsonObjectRequest(method, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = "";
                        if(method == Request.Method.POST)
                            message = "Console "+response.getString("name")+" salvo com sucesso!";
                        else
                            message = "Console "+response.getLong("id")+" atualizado com sucesso!";
                        Toast.makeText(NewConsoleActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent main = new Intent(NewConsoleActivity.this,MainActivity.class);
                        startActivity(main);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveConsole(View view){
        String url = "http://10.0.2.2:5000/api/console";
        if(id != 0)
            createConsole(url+"/"+id,Request.Method.PUT);
        else
            createConsole(url,Request.Method.POST);
    }
}
