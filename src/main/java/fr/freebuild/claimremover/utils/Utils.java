package fr.freebuild.claimremover.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;

import fr.freebuild.claimremover.ClaimRemover;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * Global Utility Class
 */
@UtilityClass
public class Utils {

  /**
   * Change String too support color
   */
  public String toColor(String toChange) {
    return ChatColor.translateAlternateColorCodes('&', toChange);
  }

  /**
   * Convert a String[] into List<String>
   *
   * @param lore Array to convert
   * @return List converted
   */
  public List<String> toList(String[] lore) {
    return (lore == null) ? null : Arrays.asList(lore);
  }

  /**
   * Map a function to a list
   *
   * @param lst The list on which apply the function
   * @param fct Function to map
   * @return The new list mapped
   */
  public <T, R> List<T> mapList(List<R> lst, Function<R, T> fct) {
    return lst.stream().map(fct).collect(Collectors.toList());
  }

  /**
   * Execute a filter on a list
   *
   * @param lst The list on which apply the function
   * @param fct Function to filter
   * @return The new list filtered
   */
  public <T> List<T> filter(List<T> lst, Predicate<T> fct) {
    return lst.stream().filter(fct).collect(Collectors.toList());
  }

  /**
   * If the index is bigger than size return the size to prevent IndexOutOfBOundException
   *
   * @param index Index to compare
   * @param size Max size
   * @return index
   */
  public int getIndex(int index, int size, Boolean exclusive) {
    if (index <= 0 || size == 0)
      return 0;
    if (exclusive)
      return (index > size) ? size : index;
    return (index >= size) ? size - 1 : index;
  }

  /**
   * If first param is null return the defaut param
   *
   * @param obj Param to get
   * @param defaut Param return when obj is null
   * @return Return obj or defaut of obj is null
   */
  public <T> T getOrElse(T obj, T defaut) {
    return Optional.ofNullable(obj).orElse(defaut);
  }
}
