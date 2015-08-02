package com.doers.games.geohangman.persistance;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.SerializableType;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Custom serializable for collections
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class SerializableCollectionsType extends SerializableType {

    /** Unique singleton **/
    private static SerializableCollectionsType singleton;

    /**
     * Default constructor
     */
    public SerializableCollectionsType() {
        super(SqlType.SERIALIZABLE, new Class<?>[0]);
    }

    /**
     * Singleton
     *
     * @return Unique singleton
     */
    public static SerializableCollectionsType getSingleton() {
        if (singleton == null) {
            singleton = new SerializableCollectionsType();
        }
        return singleton;
    }

    @Override
    public boolean isValidForField(Field field) {
        return Collection.class.isAssignableFrom(field.getType());
    }
}
