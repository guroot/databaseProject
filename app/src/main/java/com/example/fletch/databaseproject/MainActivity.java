/**
 * Ceci est une expérimentation avec le widget Spinner
 * Le code ne devrait pas être utilisé tel quel mais
 * adapté selon les besoins.
 *
 * Il est démontré que le spinner peut accepter un série d'objets
 * créées à partir de la database. Lors de la séléction d'une valeur,
 * il est possible de consulter l'objet.
 */

package com.example.fletch.databaseproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fletch.databaseproject.Model.Enseignant.Enseignant;
import com.example.fletch.databaseproject.Model.Enseignant.EnseignantDataAccess;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SchemaHelper helper = new SchemaHelper(this);

        generateSpinner(helper,(Spinner)findViewById(R.id.spinner));

    }


    /**
     *
     * Il s'agit d'un spinner alimenté à partir des données du base SqLite
     * De plus, lors de la sélection, il est possible de connaitre l'ID de la sélection
     *
     * @param helper SchemaHelper
     * @param spinner View Spinner
     */
    private void generateSpinner(SchemaHelper helper, Spinner spinner){
        EnseignantDataAccess dataAccess = new EnseignantDataAccess(helper);
        Cursor c1 = dataAccess.queryAll();
        ArrayList<Enseignant> spinnerContent = new ArrayList<Enseignant>();
        if(c1.moveToFirst()){
            do{
                Enseignant enseignant = new Enseignant();
                enseignant.setNom(c1.getString(c1.getColumnIndexOrThrow("nom")));
                enseignant.setId(Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("enseignant_id"))));
                spinnerContent.add(enseignant);
            }while(c1.moveToNext());
        }
        c1.close();

        Enseignant[] allSpinner = new Enseignant[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        ArrayAdapter<Enseignant> spinnerAdapter = new ArrayAdapter<Enseignant>(MainActivity.this,android.R.layout.simple_spinner_item, allSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Un élément a été sélectionné. Vous pouvez récupérer l'élément sélectionné en utilisant
                // parent.getItemAtPosition (position)
                ((TextView)findViewById(R.id.textView_selection))
                        .setText(String.valueOf(((Enseignant)parent.getItemAtPosition(position)).getId()));
                return;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
