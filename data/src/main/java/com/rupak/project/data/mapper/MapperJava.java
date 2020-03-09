package com.rupak.project.data.mapper;

import com.rupak.project.data.entities.artist_search.DataItem;
import com.rupak.project.data.entities.DataItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * var pictureXl: String,
 * 	var tracklist: String,
 * 	var link: String,
 * 	var pictureSmall: String,
 * 	var type: String,
 * 	var nbAlbum: Int,
 * 	var picture: String,
 * 	var nbFan: Int,
 * 	var radio: Boolean,
 * 	var pictureBig: String,
 * 	var name: String,
 * 	var id: Int,
 * 	var pictureMedium: String
 */

public class MapperJava {
    public static List<DataItemEntity> getList(List<DataItem> data){
        List<DataItemEntity> dataItemEntities = new ArrayList<>();

        for(DataItem item:data){
            DataItemEntity dataItemEntity = new DataItemEntity(
                    item.getPictureXl(),item.getTracklist(),item.getLink()
                    ,item.getPictureSmall(),item.getType(),item.getNbAlbum(),
                    item.getPicture(),item.getNbFan(),item.getRadio(),item.getPictureBig(),
                    item.getName(),item.getId(),item.getPictureMedium()
            );

            dataItemEntities.add(dataItemEntity);
        }
        return dataItemEntities;
    }
}
