package devpkjain.instasnaps.ui.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import devpkjain.instasnaps.R;
import devpkjain.instasnaps.api.vo.Comment;
import devpkjain.instasnaps.api.vo.Media;
import devpkjain.instasnaps.utils.InstagramFormatter;
import java.util.ArrayList;
import java.util.List;

import static devpkjain.instasnaps.utils.SimpleTime.formattedDuration;

public class MediaViewHolder extends RecyclerView.ViewHolder {

    public List<Comment> comments;
    @Bind(R.id.ivProfile) ImageView ivProfile;
    @Bind(R.id.tvUsername) TextView tvUsername;
    @Bind(R.id.tvTimestamp) TextView tvTimestamp;
    @Bind(R.id.ivPhoto) ImageView ivPhoto;
    @Bind(R.id.vvPhoto) VideoView vvPhoto;
    @Bind(R.id.tvLikes) TextView tvLikes;
    @Bind(R.id.tvCaption) TextView tvCaption;
    @Bind(R.id.llComments) LinearLayout llComments;

    public MediaViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(Media media) {
        Context context = itemView.getContext();
        itemView.setTag(media);
        this.comments = new ArrayList<Comment>();

        ivProfile.setBackgroundResource(R.drawable.default_profile);
        ivPhoto.setBackgroundResource(R.drawable.default_photo);

        try {
            Picasso.with(context).load(media.user == null ? "" : media.user.profile_picture).into(ivProfile);
            Picasso.with(context).load((media == null || media.user == null) ? "" : media.images.standard_resolution.url).into(ivPhoto);
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e("Picasso Error", media.toString());
        }

        if (media.videos != null && media.videos.standard_resolution != null) {
            vvPhoto.setVideoURI(Uri.parse(media.videos.standard_resolution.url));
            vvPhoto.setMediaController(new MediaController(context));
            vvPhoto.requestFocus();
            vvPhoto.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
            vvPhoto.setVisibility(View.VISIBLE);
            vvPhoto.start();
        } else {
            String defaultUri = "android.resource://" + context.getPackageName() + "/" + R.raw.default_video;
            vvPhoto.setVideoURI(Uri.parse(defaultUri));
            vvPhoto.setVisibility(View.INVISIBLE);
        }

        tvUsername.setText(InstagramFormatter.formatComment(media.user == null ? "" : media.user.username));
        tvTimestamp.setText(formattedDuration(media.created_time == null ? "" : media.created_time));

        tvLikes.setText(InstagramFormatter.formatLikes(media.likes == null ? "" : media.likes.toString()));
        tvCaption.setText(InstagramFormatter.formatComment(media.caption == null ? "" : media.caption.text));

        llComments.removeAllViews();
        if (media.comments != null) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            for (Comment comment : media.comments.data) {
                TextView tvComment = (TextView) inflater.inflate(R.layout.item_comment, null);
                tvComment.setText(InstagramFormatter.formatComment(comment.text));
                llComments.addView(tvComment);
            }
        }
    }
}
