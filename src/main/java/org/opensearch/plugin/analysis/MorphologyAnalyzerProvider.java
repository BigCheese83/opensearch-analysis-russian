package org.opensearch.plugin.analysis;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.analyzer.MorphologyAnalyzer;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.IndexSettings;
import org.opensearch.index.analysis.AbstractIndexAnalyzerProvider;

public class MorphologyAnalyzerProvider extends AbstractIndexAnalyzerProvider<MorphologyAnalyzer> {

    private final MorphologyAnalyzer analyzer;

    public MorphologyAnalyzerProvider(IndexSettings indexSettings, Environment environment, String name,
                                      Settings settings, LuceneMorphology luceneMorphology) {
        super(indexSettings, name, settings);
        analyzer = new MorphologyAnalyzer(luceneMorphology);
    }

    @Override
    public MorphologyAnalyzer get() {
        return this.analyzer;
    }
}
