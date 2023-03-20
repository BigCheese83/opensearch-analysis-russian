package org.opensearch.plugin.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.opensearch.index.analysis.AnalyzerProvider;
import org.opensearch.index.analysis.TokenFilterFactory;
import org.opensearch.indices.analysis.AnalysisModule;
import org.opensearch.plugins.AnalysisPlugin;
import org.opensearch.plugins.Plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnalysisRussianPlugin extends Plugin implements AnalysisPlugin {

    private final RussianLuceneMorphology russianLuceneMorphology;
    private final EnglishLuceneMorphology englishLuceneMorphology;

    public AnalysisRussianPlugin() {
        super();

        try {
            russianLuceneMorphology = new RussianLuceneMorphology();
        } catch (IOException e) {
            throw new IllegalStateException("unable to load russian morphology info", e);
        }

        try {
            englishLuceneMorphology = new EnglishLuceneMorphology();
        } catch (IOException e) {
            throw new IllegalStateException("unable to load english morphology info", e);
        }
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> extra = new HashMap<>();

        extra.put("russian_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyTokenFilterFactory(indexSettings, environment, name, settings, russianLuceneMorphology));

        extra.put("english_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyTokenFilterFactory(indexSettings, environment, name, settings, englishLuceneMorphology));

        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();

        extra.put("russian_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyAnalyzerProvider(indexSettings, environment, name, settings, russianLuceneMorphology));

        extra.put("english_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyAnalyzerProvider(indexSettings, environment, name, settings, englishLuceneMorphology));

        return extra;
    }
}
