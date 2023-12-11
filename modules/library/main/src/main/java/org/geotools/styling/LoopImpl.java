package org.geotools.styling;

import java.util.*;
import org.geotools.api.metadata.citation.OnLineResource;
import org.geotools.api.style.*;
import org.geotools.api.util.Cloneable;
import org.geotools.util.Utilities;

public class LoopImpl implements Loop, Cloneable {
    private List<Rule> rules = new ArrayList<>();
    private GraphicLegend legend;
    private String name;
    private String maxIndex;
    private String minIndex;
    private String indexName;
    private DescriptionImpl description = new DescriptionImpl();
    private OnLineResource online = null;
    protected Map<String, String> options;

    /** Creates a new instance of DefaultLoop */
    public LoopImpl() {}

    /** Creates a new instance of DefaultLoop */
    protected LoopImpl(Rule... rules) {
        this.rules.addAll(Arrays.asList(rules));
    }

    protected LoopImpl(
            Rule[] rules,
            Description desc,
            Graphic legend,
            String name,
            String minIndex,
            String maxIndex,
            String indexName) {
        this.rules = new ArrayList<>(Arrays.asList(rules));
        description.setAbstract(desc.getAbstract());
        description.setTitle(desc.getTitle());
        this.legend = (GraphicLegend) legend;
        this.name = name;
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.indexName = indexName;
    }

    /** Copy constructor */
    public LoopImpl(Loop loop) {
        this.rules = new ArrayList<>();
        for (org.geotools.api.style.Rule sym : loop.rules()) {
            if (sym != null) {
                this.rules.add((Rule) sym);
            }
        }
        if (loop.getDescription() != null && loop.getDescription().getTitle() != null) {
            this.description.setTitle(loop.getDescription().getTitle());
        }
        if (loop.getDescription() != null && loop.getDescription().getAbstract() != null) {
            this.description.setTitle(loop.getDescription().getAbstract());
        }
        if (loop.getLegend() != null) {
            this.legend = loop.getLegend();
        }
        this.name = loop.getName();
        this.maxIndex = loop.getMaxIndex();
        this.minIndex = loop.getMinIndex();
        this.indexName = loop.getIndexName();
    }

    @Override
    public GraphicLegend getLegend() {
        return legend;
    }

    @Override
    public void setLegend(GraphicLegend legend) {
        this.legend = legend;
    }

    @Override
    public List<Rule> rules() {
        return rules;
    }

    @Override
    public Rule[] getRules() {

        final Rule[] ret = new Rule[rules.size()];
        for (int i = 0, n = rules.size(); i < n; i++) {
            ret[i] = rules.get(i);
        }

        return ret;
    }

    @Override
    public DescriptionImpl getDescription() {
        return description;
    }

    @Override
    public void setDescription(org.geotools.api.style.Description description) {
        this.description = DescriptionImpl.cast(description);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object accept(TraversingStyleVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    @Override
    public void accept(StyleVisitor visitor) {
        visitor.visit(this);
    }

    /** Creates a deep copy clone of the loop. */
    @Override
    public Object clone() {
        try {
            LoopImpl clone = (LoopImpl) super.clone();

            clone.name = name;
            clone.description.setAbstract(description.getAbstract());
            clone.description.setTitle(description.getTitle());
            clone.legend = legend;
            clone.minIndex = minIndex;
            clone.maxIndex = maxIndex;
            clone.indexName = indexName;

            clone.rules = new ArrayList<>(rules);
            clone.options = options;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("This will never happen", e);
        }
    }

    /**
     * Generates a hashcode for the Loop.
     *
     * <p>For complex styles this can be an expensive operation since the hash code is computed
     * using all the hashcodes of the object within the style.
     *
     * @return The hashcode.
     */
    @Override
    public int hashCode() {
        final int PRIME = 1000003;
        int result = 0;
        result = (PRIME * result) + rules.hashCode();

        if (legend != null) result = (PRIME * result) + legend.hashCode();

        if (name != null) {
            result = (PRIME * result) + name.hashCode();
        }

        if (description != null) {
            result = (PRIME * result) + description.hashCode();
        }

        if (minIndex != null) {
            result = (PRIME * result) + minIndex.hashCode();
        }

        if (maxIndex != null) {
            result = (PRIME * result) + maxIndex.hashCode();
        }

        if (indexName != null) {
            result = (PRIME * result) + indexName.hashCode();
        }

        if (options != null) {
            result = (PRIME * result) + options.hashCode();
        }

        return result;
    }

    /**
     * Compares this Loop with another for equality.
     *
     * <p>Two LoopImpls are equal if all their properties are equal.
     *
     * <p>For complex styles this can be an expensive operation since it checks all objects for
     * equality.
     *
     * @param oth The other loop to compare with.
     * @return True if this and oth are equal.
     */
    @Override
    public boolean equals(Object oth) {
        if (this == oth) {
            return true;
        }

        if (oth instanceof LoopImpl) {
            LoopImpl other = (LoopImpl) oth;

            return Utilities.equals(name, other.name)
                    && Utilities.equals(description, other.description)
                    && Utilities.equals(minIndex, other.minIndex)
                    && Utilities.equals(maxIndex, other.maxIndex)
                    && Utilities.equals(indexName, other.indexName)
                    && Utilities.equals(legend, other.legend)
                    && Utilities.equals(rules, other.rules)
                    && getOptions().equals(other.getOptions());
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<LoopImpl");
        if (name != null) {
            buf.append(":");
            buf.append(name);
        }
        if (minIndex != null) {
            buf.append(", index_min=");
            buf.append(minIndex);
        }
        if (maxIndex != null) {
            buf.append(", index_lower_than=");
            buf.append(maxIndex);
        }
        if (maxIndex != null) {
            buf.append(", index_name=");
            buf.append(indexName);
        }
        buf.append("> ");
        if (rules != null) {
            buf.append("\n");
            for (Rule rule : rules) {
                buf.append("\t");
                buf.append(rule);
                buf.append("\n");
            }
        }
        buf.append(getOptions());
        return buf.toString();
    }

    @Override
    public OnLineResource getOnlineResource() {
        return online;
    }

    @Override
    public void setOnlineResource(OnLineResource online) {
        this.online = online;
    }

    static LoopImpl cast(Loop loop) {
        if (loop == null) {
            return null;
        } else if (loop instanceof LoopImpl) {
            return (LoopImpl) loop;
        } else {
            return new LoopImpl(loop);
        }
    }

    @Override
    public boolean hasOption(String key) {
        return options != null && options.containsKey(key);
    }

    @Override
    public Map<String, String> getOptions() {
        if (options == null) {
            options = new LinkedHashMap<>();
        }
        return options;
    }

    @Override
    public String getMaxIndex() {
        return maxIndex;
    }

    @Override
    public void setMaxIndex(String maxIndex) {
        this.maxIndex = maxIndex;
    }

    @Override
    public String getMinIndex() {
        return minIndex;
    }

    @Override
    public void setMinIndex(String minIndex) {
        this.minIndex = minIndex;
    }

    @Override
    public String getIndexName() {
        return indexName;
    }

    @Override
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
