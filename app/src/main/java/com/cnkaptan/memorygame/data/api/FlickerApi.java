package com.cnkaptan.memorygame;

import com.cnkaptan.memorygame.model.PhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by cnkaptan on 06/09/17.
 */

public interface FlickerApi {

    @GET("?api_key=387b34c39c161951f2976613d9ec0863&format=json&nojsoncallback=1&method=flickr.photos.search&tags=bestofcats")
    Observable<PhotosResponse> getCatPhotos();
}
