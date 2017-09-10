package com.cnkaptan.memorygame.data.api;

import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.model.FlickrPhoto;

import java.util.List;

import rx.Observable;

/**
 * Created by cnkaptan on 07/09/2017.
 */

public interface RemoteSource {
    Observable<List<FlickrPhoto>> getCatPhotos();
}
