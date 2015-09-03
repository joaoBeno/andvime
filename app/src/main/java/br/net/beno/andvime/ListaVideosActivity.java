package br.net.beno.andvime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaVideosActivity extends AppCompatActivity {

    public List<Video> videos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_videos);

        RecyclerView rv = (RecyclerView)findViewById(R.id.view_recicla);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        for(int i = 0;i<10;i++) {
            videos.add(new Video(i,"Um teste qualquer","Apenas um simples teste para ver o que rola",120,"aaa/bbb","imagem.png"));
        }

        RVAdapter adapter = new RVAdapter(videos);
        rv.setAdapter(adapter);

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
}
