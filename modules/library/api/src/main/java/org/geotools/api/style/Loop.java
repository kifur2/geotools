package org.geotools.api.style;

import java.util.*;
import org.geotools.api.metadata.citation.OnLineResource;

public interface Loop {

    /**
     * Returns a name for this loop. This can be any string that uniquely identifies this loop
     * within a given canvas. It is not meant to be human-friendly. (The "title" property is meant
     * to be human friendly.)
     *
     * @return a name for this loop.
     */
    String getName();

    /**
     * Sets the name of the loop.
     *
     * @param name The name of the loop. This provides a way to identify a loop.
     */
    void setName(String name);

    /**
     * Description for this loop.
     *
     * @return Human readable description for use in user interfaces
     * @since 2.5.x
     */
    Description getDescription();

    /** */
    GraphicLegend getLegend();

    /**
     * Description for this loop.
     *
     * @param description Human readable title and abstract.
     */
    void setDescription(Description description);

    /** @param legend */
    void setLegend(GraphicLegend legend);

    Rule[] getRules();

    List<Rule> rules();

    /** @return Location where this style is defined; file or server; or null if unknown */
    OnLineResource getOnlineResource();

    /** @param resource Indicates where this style is defined */
    void setOnlineResource(OnLineResource resource);

    /** Determines if a vendor option with the specific key has been set on this Rule. */
    default boolean hasOption(String key) {
        return false;
    }

    /**
     * Map of vendor options for the Rule.
     *
     * <p>Client code looking for the existence of a single option should use {@link
     * #hasOption(String)}
     */
    default Map<String, String> getOptions() {
        return new HashMap<>();
    }

    String getMaxIndex();

    void setMaxIndex(String maxIndex);

    String getMinIndex();

    void setMinIndex(String minIndex);

    String getIndexName();

    void setIndexName(String indexName);

    /**
     * calls the visit method of a StyleVisitor
     *
     * @param visitor the style visitor
     */
    Object accept(TraversingStyleVisitor visitor, Object extraData);

    /** Used to traverse the style data structure. */
    void accept(StyleVisitor visitor);
}
