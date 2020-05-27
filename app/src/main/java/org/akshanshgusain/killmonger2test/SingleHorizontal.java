package org.akshanshgusain.killmonger2test;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleHorizontal {
          private int status;
          private String image, name;

    public SingleHorizontal(int status, String image, String name) {
        this.status = status;
        this.image = image;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
