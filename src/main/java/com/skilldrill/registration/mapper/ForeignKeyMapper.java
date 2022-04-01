package com.skilldrill.registration.mapper;

import org.bson.types.ObjectId;

public interface ForeignKeyMapper {

    default String toString(ObjectId objectId) {
        if(objectId == null)
            return null;
        return String.valueOf(objectId);
    }

    default ObjectId toObjectId(String string) {
        if(string == null)
            return null;
        return new ObjectId(string);
    }
}
