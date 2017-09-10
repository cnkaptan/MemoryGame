package com.cnkaptan.memorygame.data.api;

import android.util.Log;

import com.cnkaptan.memorygame.FlickerApi;
import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.model.FlickrPhoto;
import com.cnkaptan.memorygame.model.PhotosResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cnkaptan on 07/09/2017.
 */
@Singleton
public class RemouteSourceImpl implements RemoteSource{
    private FlickerApi flickerApi;

    public RemouteSourceImpl(FlickerApi flickerApi) {
        this.flickerApi = flickerApi;
    }

    @Override
    public Observable<List<FlickrPhoto>> getCatPhotos() {
        return flickerApi.getCatPhotos().subscribeOn(Schedulers.io()).flatMap(new Func1<PhotosResponse, Observable<FlickrPhoto>>() {
            @Override
            public Observable<FlickrPhoto> call(PhotosResponse photosResponse) {
                List<FlickrPhoto> flickrPhotos = photosResponse.getPhotos().getPhotos();
                Collections.shuffle(flickrPhotos);
                return Observable.from(flickrPhotos);
            }
        }).toList();
    }
}
