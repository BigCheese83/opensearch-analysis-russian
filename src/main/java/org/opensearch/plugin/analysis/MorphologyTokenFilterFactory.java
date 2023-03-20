package org.opensearch.plugin.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.analyzer.MorphologyFilter;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.IndexSettings;
import org.opensearch.index.analysis.AbstractTokenFilterFactory;

public class MorphologyTokenFilterFactory extends AbstractTokenFilterFactory {

    private final LuceneMorphology luceneMorph;

    public MorphologyTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name,
                                        Settings settings, LuceneMorphology englishLuceneMorphology) {
        super(indexSettings, name, settings);
        luceneMorph = englishLuceneMorphology;
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new MorphologyFilter(tokenStream, luceneMorph);
    }
}
