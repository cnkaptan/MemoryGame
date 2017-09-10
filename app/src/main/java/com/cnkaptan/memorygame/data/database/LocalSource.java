package com.cnkaptan.memorygame.data.database;

import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.model.FlickrPhoto;

import java.util.List;

import rx.Observable;

/**
 * Created by cnkaptan on 07/09/2017.
 */

public interface LocalSource {
    void saveCatPhotos(List<FlickrPhoto> flickrPhotos);
    Observable<List<FlickrPhoto>> getCatPhotos();
}
