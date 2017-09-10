package com.cnkaptan.memorygame.data;

import android.util.Log;

import com.cnkaptan.memorygame.data.api.RemoteSource;
import com.cnkaptan.memorygame.data.database.LocalSource;
import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.FlickerApi;
import com.cnkaptan.memorygame.model.FlickrPhoto;
import com.cnkaptan.memorygame.model.PhotosResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cnkaptan on 07/09/2017.
 */

public class DataManagerImpl implements DataManager {
    private RemoteSource remoteSource;
    private LocalSource localSource;

    public DataManagerImpl(RemoteSource remoteSource, LocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    @Override
    public Observable<List<ChecableItem>> getPhotos(final int cellNumber) {
        return Observable.concat(localSource.getCatPhotos(),
                remoteSource.getCatPhotos().doOnNext(
                        new Action1<List<FlickrPhoto>>() {
                            @Override
                            public void call(List<FlickrPhoto> flickrPhotos) {
                                localSource.saveCatPhotos(flickrPhotos);
                            }
                        }
                ))
                .doOnNext(new Action1<List<FlickrPhoto>>() {
                    @Override
                    public void call(List<FlickrPhoto> flickrPhotos) {
                        Log.e("datamanager", flickrPhotos.toString());
                    }
                })
                .first()
                .flatMap(new Func1<List<FlickrPhoto>, Observable<List<ChecableItem>>>() {
                    @Override
                    public Observable<List<ChecableItem>> call(List<FlickrPhoto> flickrPhotos) {
                        final List<ChecableItem> photos = new ArrayList<>();
                        for (int i = 0; i < cellNumber * cellNumber / 2; i++) {
                            FlickrPhoto flickrPhoto = flickrPhotos.get(i);
                            photos.add(new ChecableItem(flickrPhoto.getPhotoUrl(), false));
                            photos.add(new ChecableItem(flickrPhoto.getPhotoUrl(), false));
                        }
                        Collections.shuffle(photos);
                        return Observable.from(photos).toList();
                    }
                });
    }
}
