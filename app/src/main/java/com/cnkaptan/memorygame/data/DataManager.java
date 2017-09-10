package com.cnkaptan.memorygame.data;

import com.cnkaptan.memorygame.model.ChecableItem;

import java.util.List;

import rx.Observable;

/**
 * Created by cnkaptan on 07/09/2017.
 */

public interface DataManager {
    Observable<List<ChecableItem>> getPhotos(int cellNumber);
}
