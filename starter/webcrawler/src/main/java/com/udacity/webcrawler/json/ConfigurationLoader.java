package com.udacity.webcrawler.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() {
    // TODO: Fill in this method.
    try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
      return read(bufferedReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new CrawlerConfiguration.Builder().build();
    //try (Reader reader = Files.newBufferedReader(path)) {
    //  ObjectMapper objectMapper = new ObjectMapper();
    //  CrawlerConfiguration crawlerConfiguration = objectMapper.readValue(reader, CrawlerConfiguration.class);
    //  return crawlerConfiguration;
    //} catch (Exception e) {
    //  e.printStackTrace();
    //  return null;
    //}
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) {
    // reader is JSON input
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(reader);
    // TODO: Fill in this method
    // this method needs to: read the JSON input and parse it into a
    // `CrawlerConfiguration` using the Jackson JSON library.
    ObjectMapper objectMapper = new ObjectMapper();
    //objectMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
    try {
      return objectMapper.readValue(reader, CrawlerConfiguration.Builder.class).build();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }
}
