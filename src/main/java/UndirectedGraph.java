public interface UndirectedGraph<T> {

    /**
     * Lägger till en ny nod i grafen.
     *
     * @param newNode
     *            datat för den nya noden som ska läggas till i grafen.
     * @return false om noden redan finns.
     */
    boolean add(T newNode);

    /**
     * Kopplar samman två noder i grafen. Eftersom grafen är oriktad så spelar
     * det ingen roll vilken av noderna som står först. Det är också
     * fullständigt okej att koppla ihop en nod med sig själv. Däremot tillåts
     * inte multigrafer. Om två noder försöks kopplas ihop som redan är
     * ihopkopplade uppdateras bara deras kostnadsfunktion.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @param cost
     *            kostnaden för att ta sig mellan noderna.
     * @return true om bägge noderna finns i grafen och kan kopplas ihop.
     */
    boolean connect(T node1, T node2, int cost);

    /**
     * Returnerar kostnaden för att ta sig mellan två noder.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @return kostnaden för att ta sig mellan noderna.
     */
    int getCost(T node1, T node2);

    /**
     * Returnerar en ny graf som utgör ett minimalt spännande träd till grafen.
     *
     * @return en graf som representerar ett minimalt spännande träd.
     */
    UndirectedGraph<T> minimumSpanningTree();
}
