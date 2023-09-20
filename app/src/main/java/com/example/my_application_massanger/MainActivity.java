package com.example.my_application_massanger;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
import java.io.UnsupportedEncodingException;



import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send_button = findViewById(R.id.send_button);
        EditText editText = findViewById(R.id.sended_text);

        String hostName = "178.74.237.245"; // Ім'я хоста сервера
        int portNumber = 25566; // Порт сервера

        send_button.setOnClickListener(view -> {
            // Отримайте текст з EditText при натисканні кнопки
            String text = editText.getText().toString();
            try {
                byte[] utf8Bytes = text.getBytes("UTF-8");
                // Далі відправте utf8Bytes на сервер
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                // Обробка помилки кодування
            }
// Далі відправте utf8Bytes на сервер

            // Встановіть з'єднання з сервером та надішліть текст
            new Thread(() -> {
                try (
                        Socket socket = new Socket(hostName, portNumber);
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    Log.d("MainActivity", "Підключено до сервера.");

                    // Відправити текст на сервер
                    out.println(text);

                    // Отримати відповідь від сервера і вивести її
                    String response = in.readLine();
                    Log.d("MainActivity", "Від сервера: " + response);
                } catch (UnknownHostException e) {
                    Log.e("MainActivity", "Не відомий хост: " + hostName);
                } catch (IOException e) {
                    Log.e("MainActivity", "Помилка при спробі з'єднання до сервера: " + e.getMessage());
                }
            }).start();
        });

    }
}
