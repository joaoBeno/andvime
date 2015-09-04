package br.net.beno.andvime;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaVideosActivity extends AppCompatActivity {

    public List<Video> videos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_videos);

        CarregandoDialogFragment cdf = new CarregandoDialogFragment();
        cdf.show(getSupportFragmentManager(),"tag");

        RecyclerView rv = (RecyclerView)findViewById(R.id.view_recicla);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        for(int i = 0;i<10;i++) {
            videos.add(new Video(i,"Um teste qualquer","Apenas um simples teste para ver o que rola",120,"aaa/bbb","imagem.png"));
        }

        RVAdapter adapter = new RVAdapter(videos);
        rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        LinearLayout searchBar = (LinearLayout) searchView.findViewById(R.id.search_bar);
        searchBar.setLayoutTransition(new LayoutTransition());

        return true;
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.VideoViewHolder>{

        List<Video> videos;

        RVAdapter(List<Video> videos){
            this.videos = videos;
        }

        public class VideoViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView videoTitulo;
            TextView videoDuracao;
            //ImageView personPhoto;

            VideoViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.card_video);
                videoTitulo = (TextView)itemView.findViewById(R.id.textViewUm);
                videoDuracao = (TextView)itemView.findViewById(R.id.textViewDois);
                //personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            }
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_videos, viewGroup, false);
            VideoViewHolder pvh = new VideoViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(VideoViewHolder videoViewHolder, int i) {
            videoViewHolder.videoTitulo.setText(videos.get(i).titulo);
            videoViewHolder.videoDuracao.setText(videos.get(i).duracao);
            //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    public static class CarregandoDialogFragment extends AppCompatDialogFragment {
        @Override
        public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Por favor aguarde...").setTitle("Carregando vídeos");
                    /*.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });*/
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


}
