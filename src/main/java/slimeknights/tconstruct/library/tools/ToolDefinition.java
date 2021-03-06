package slimeknights.tconstruct.library.tools;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.common.util.Lazy;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * The data defining a tinkers tool, e.g. a pickaxe or a hammer.
 * Note that this defines the tool metadata itself, not an instance of the tool.
 * Contains information about what's needed to craft the tool, how it behaves...
 */
public class ToolDefinition {
  private static final Set<MaterialStatsId> REPAIR_STATS = ImmutableSet.of(HeadMaterialStats.ID);

  /**
   * Inherent stats of the tool.
   */
  private final ToolBaseStatDefinition baseStatDefinition;
  /**
   * The tool parts required to build this tool.
   */
  protected final Lazy<List<IToolPart>> requiredComponents;
  /**
   * Categories determine behaviour of the tool when interacting with things or displaying information.
   */
  protected final Set<Category> categories;

  private int[] repairIndices;

  public ToolDefinition(ToolBaseStatDefinition baseStatDefinition, Supplier<List<IToolPart>> requiredComponents, Set<Category> categories) {
    this.baseStatDefinition = baseStatDefinition;
    this.requiredComponents = Lazy.of(requiredComponents);
    this.categories = ImmutableSet.copyOf(categories);
  }

  /**
   * Gets the current tools base stats definition
   *
   * @return the tools base stats definition
   */
  public ToolBaseStatDefinition getBaseStatDefinition() {
    return this.baseStatDefinition;
  }

  /**
   * Gets the required components for the given tool definition
   * @return the required components
   */
  public List<IToolPart> getRequiredComponents() {
    return this.requiredComponents.get();
  }

  /**
   * Checks if the tool has the given category or not
   *
   * @param category the category to check for
   * @return if the tool has the category or not
   */
  public boolean hasCategory(Category category) {
    return this.categories.contains(category);
  }

  /**
   * Gets all the categories for the given tool
   *
   * @return the list of categories
   */
  public Set<Category> getCategories() {
    return this.categories;
  }

  /* Repairing */

  /** Returns a list of part material requirements for repair materials */
  public int[] getRepairParts() {
    if (repairIndices == null) {
      // get indices of all head parts
      List<IToolPart> components = requiredComponents.get();
      repairIndices = IntStream.range(0, components.size())
                               .filter(i -> REPAIR_STATS.contains(components.get(i).getStatType()))
                               .toArray();
    }
    return repairIndices;
  }

  /*public float getRepairModifierForPart(int index) {
    return 1f;
  }*/
}
