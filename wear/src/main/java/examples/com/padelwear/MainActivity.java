package examples.com.padelwear;

import android.content.Intent;
import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends WearableActivity implements DataClient.OnDataChangedListener {
    // Elementos a mostrar en la lista
    String[] elementos = {"Partida", "Terminar partida", "Historial",
            "Notificación", "Pasos", "Pulsaciones", "Swipe Dismiss" };

    private static final String WEAR_ARRANCAR_ACTIVIDAD="/arrancar_actividad";

    private static final String KEY_CONTADOR = "com.example.key.contador";
    private static final String ITEM_CONTADOR = "/contador";
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WearableRecyclerView lista = (WearableRecyclerView)
                findViewById(R.id.lista);
        lista.setEdgeItemsCenteringEnabled(true);
        lista.setLayoutManager(new WearableLinearLayoutManager(this,
                new CustomLayoutCallback()));
        Adaptador adaptador = new Adaptador(this, elementos);
        adaptador.setOnItemClickListener(
            new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Integer tag = (Integer) v.getTag();
                     //Toast.makeText(MainActivity.this, "Elegida opción:" + tag,Toast.LENGTH_SHORT).show();
                     switch (tag) {
                         case 0:
                             startActivity(new Intent(MainActivity.this, Contador.class));
                             break;
                         case 1:
                             startActivity(new Intent(MainActivity.this, Confirmacion.class));
                             break;
                         case 2:
                             startActivity(new Intent(MainActivity.this, Historial.class));
                             break;
                         case 4:
                             startActivity(new Intent(MainActivity.this, Pasos.class));
                             break;
                         case 6:
                             startActivity(new Intent(MainActivity.this, SwipeDismiss.class));
                             break;
                     }
                 }
            }
        );
        lista.setAdapter(adaptador);
        lista.setCircularScrollingGestureEnabled(true);
        lista.setScrollDegreesPerScreen(180);
        lista.setBezelFraction(0.5f);
    }
    @Override protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }
    @Override protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }

    @Override public void onDataChanged(DataEventBuffer eventos) {
        for (DataEvent evento : eventos) {
            if (evento.getType() == DataEvent.TYPE_CHANGED) {
                DataItem item = evento.getDataItem();
                if (item.getUri().getPath().equals(ITEM_CONTADOR)) {
                    DataMap dataMap = DataMapItem.fromDataItem(item)
                            .getDataMap();
                    contador = dataMap.getInt(KEY_CONTADOR);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            ((TextView) findViewById(R.id.textoContador))
                                    .setText(Integer.toString(contador));
                        }
                    });
                }
            } else if (evento.getType() == DataEvent.TYPE_DELETED) {
                // Algún ítem ha sido borrado
            }
        }
    }
}
