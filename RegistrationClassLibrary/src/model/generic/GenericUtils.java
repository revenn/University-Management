package model.generic;

import java.util.Collection;
import java.util.List;

public abstract class GenericUtils {

    public static <T> boolean addToCollection(Collection<T> collection, T element) {
        if (!collection.contains(element)) {
            collection.add(element);
            return true;
        }
        return false;
    }

    public static <T> T searchInList(List<T> collection, T element) {
        int index = collection.indexOf(element);

        if (index != -1){
            return collection.get(index);
        } else {
            return null;
        }
    }
}
