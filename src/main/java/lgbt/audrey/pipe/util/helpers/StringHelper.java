package lgbt.audrey.pipe.util.helpers;

/**
 * @author audrey
 * @since 12/19/15.
 */
public final class StringHelper {
    private StringHelper() {
    }

    /**
     * I did not write this method. This was taken from
     * <a href="http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java">Wikibooks' article</a>
     * on implementing Levenshtein Distance in various languages.
     *
     * @param s0 First string to compare with
     * @param s1 Second string to compare with
     * @return The Levenshtein Distance between the two Strings
     */
    public static int levenshteinDistance(final CharSequence s0, final CharSequence s1) {
        final int len0 = s0.length() + 1;
        final int len1 = s1.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for(int i = 0; i < len0; i++) {
            cost[i] = i;
        }

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for(int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                final int match = s0.charAt(i - 1) == s1.charAt(j - 1) ? 0 : 1;

                // computing cost for each transformation
                final int cost_replace = cost[i - 1] + match;
                final int cost_insert = cost[i] + 1;
                final int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            final int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    public static String[] removeFirst(final String[] args) {
        final String[] ret = new String[args.length - 1];
        System.arraycopy(args, 1, ret, 0, ret.length);
        return ret;
    }
}
