package com.cnkaptan.memorygame.data.database;

import com.cnkaptan.memorygame.FlickerApi;
import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.model.FlickrPhoto;

import java.util.Collections;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import rx.Observable;

/**
 * Created by cnkaptan on 07/09/2017.
 */

public class LocalSourceImpl implements LocalSource {
    private BoxStore boxStore;

    public LocalSourceImpl(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    @Override
    public void saveCatPhotos(List<FlickrPhoto> flickrPhotos) {
        final Box<FlickrPhoto> photoBox = boxStore.boxFor(FlickrPhoto.class);
        photoBox.put(flickrPhotos);
    }

    @Override
    public Observable<List<FlickrPhoto>> getCatPhotos() {
        final Box<FlickrPhoto> photoBox = boxStore.boxFor(FlickrPhoto.class);
        List<FlickrPhoto> flickrPhotos = photoBox.query().build().find();
        if (flickrPhotos.size() == 0){
            return Observable.empty();
        }
        Collections.shuffle(flickrPhotos);
        return Observable.from(flickrPhotos).toList();
    }
}
