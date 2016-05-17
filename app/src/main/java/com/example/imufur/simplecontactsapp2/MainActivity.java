package com.example.imufur.simplecontactsapp2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<String>();
        contacts.add("grgreg | 975456456");
        contacts.add("qwwertyy | 12345674");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, contacts);
        ListView listView = (ListView) findViewById(R.id.listView_contacts);
        listView.setAdapter(adapter);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter_spinner = ArrayAdapter.createFromResource(this,
                R.array.contacts_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter_spinner);


        //apagar contatos
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //codigo executado quando cica item da lista

               ListView listView = (ListView) findViewById(R.id.listView_contacts);

               String item = (String) listView.getItemAtPosition(position);


               contacts.remove(position);

               ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,//mostrar lista atualizada
                       android.R.layout.simple_list_item_1, contacts);

               listView.setAdapter(adapter);

               Toast.makeText(MainActivity.this, "apagou o contacto " + item, Toast.LENGTH_SHORT).show();



           }
       });


    }

    public void onClick_search(View view) {
        // ir buscar referência para a edittext, spinner e listview
        EditText et = (EditText) findViewById(R.id.editText_search);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ListView lv = (ListView) findViewById(R.id.listView_contacts);

        // ir à edittext buscar o termo a pesquisar
        String termo = et.getText().toString();

        if (termo.equals("")) { // se o termo a pesquisar for uma string vazia
            // mostra os contactos todos
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, contacts);
            lv.setAdapter(adapter);

            Toast.makeText(MainActivity.this, "Showing all contacts.", Toast.LENGTH_SHORT).show();
        } else { // se houver algo para pesquisar

            String itemSeleccionado = (String) sp.getSelectedItem();

            // pesquisar o termo nos contactos, e guardar o resultado da pesquisa
            // numa lista nova
            ArrayList<String> resultados = new ArrayList<>();

            if (itemSeleccionado.equals("All")) {
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);

                    boolean contem = c.contains(termo);

                    if (contem == true) {
                        resultados.add(c);
                    }
                }
            } else if (itemSeleccionado.equals("Name")) {
                // código pesquisar só no nome
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);

                    String[] s = c.split("\\|");
                    String name = s[0];

                    boolean contem = name.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }
            } else if (itemSeleccionado.equals("Phone")) {
                // códido pesquisar só no telefone
                for (int i = 0; i < contacts.size(); i++) {
                    String c = contacts.get(i);

                    String[] s = c.split("\\|");
                    String number = s[1];

                    boolean contem = number.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }
            }

            boolean vazia = resultados.isEmpty();

            if (vazia == false) {
                // mostrar na listview a lista nova que contém o resultado da pesquisa
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1, resultados);
                lv.setAdapter(adapter);

                // mostrar uma mensagem a dizer que a pesquisa teve sucesso
                Toast.makeText(MainActivity.this, "Showing searched contacts.", Toast.LENGTH_SHORT).show();
            } else { // se a lista está vazia
                // mostra os contactos todos
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1, contacts);
                lv.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "No results found. Showing all contacts.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //pesquisar o termo nos contactos e guardar o resultado da pesuqisa numa lista nova




    public void onClick_add(View view) {AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialogwindow, null));
// Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                //ir buscar as edit texts
                AlertDialog al = (AlertDialog) dialog;

                EditText etname = (EditText) al.findViewById(R.id.editText_name);
                EditText etphone = (EditText) al.findViewById(R.id.editText_phone);

                //ir buscar as strings das edit texts

                String name = etname.getText().toString();
                String phone = etphone.getText().toString();

                //criar um novo contacto

                String contact = name + " | " + phone;

                //adicionar o contacto a lista de contactos

                contacts.add(contact);

                //mostrar a lista de contactos atualizada


                ListView listView = (ListView) findViewById(R.id.listView_contacts);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1, contacts);
                listView.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "New contact added", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Toast.makeText(MainActivity.this, "Contact not added", Toast.LENGTH_SHORT).show();
            }
        });
// Set other dialog properties
        builder.setTitle("New Contact");
        builder.setMessage("Enter the name and phone of the new contact and press ok");

// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
