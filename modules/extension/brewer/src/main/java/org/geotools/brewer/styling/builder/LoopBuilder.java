package org.geotools.brewer.styling.builder;

import java.util.*;
import org.geotools.api.style.GraphicLegend;
import org.geotools.api.style.Loop;
import org.geotools.api.style.Rule;

public class LoopBuilder extends AbstractStyleBuilder<Loop> {
    List<Rule> rules = new ArrayList<>();

    Builder<? extends Rule> ruleBuilder;

    String maxIndex;

    private GraphicLegendBuilder legend = new GraphicLegendBuilder(this).unset();

    public LoopBuilder() {
        this(null);
    }

    public LoopBuilder(FeatureTypeStyleBuilder parent) {
        super(parent);
        reset();
    }

    public LoopBuilder maxIndex(String maxIndex) {
        unset = false;
        this.maxIndex = maxIndex;
        return this;
    }

    public GraphicLegendBuilder legend() {
        unset = false;
        return legend;
    }

    @Override
    public Loop build() {
        if (unset) {
            return null;
        }
        if (ruleBuilder == null && rules.isEmpty()) {
            ruleBuilder = new RuleBuilder();
        }
        if (ruleBuilder != null) {
            rules.add(ruleBuilder.build());
        }

        Loop loop = sf.createLoop();
        loop.rules().addAll(rules);
        loop.setMaxIndex(maxIndex);
        GraphicLegend gl = legend.build();
        if (gl != null) {
            loop.setLegend(gl);
        }
        if (parent == null) {
            reset();
        }
        return loop;
    }

    @Override
    public LoopBuilder unset() {
        return (LoopBuilder) super.unset();
    }

    @Override
    public LoopBuilder reset() {
        maxIndex = "";
        rules.clear();
        legend.unset();
        unset = false;
        return this;
    }

    @Override
    public LoopBuilder reset(Loop loop) {
        if (loop == null) {
            return unset();
        }
        maxIndex = loop.getMaxIndex();
        rules.clear();
        rules.addAll(loop.rules()); // TODO: unpack into builders in order to "copy"
        ruleBuilder = null;
        unset = false;
        legend.reset(loop.getLegend());
        return this;
    }

    @Override
    protected void buildStyleInternal(StyleBuilder sb) {
        sb.featureTypeStyle().loop().init(this);
    }
}
