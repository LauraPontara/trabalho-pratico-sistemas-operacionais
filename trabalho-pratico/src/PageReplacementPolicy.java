import java.util.Set;

public interface PageReplacementPolicy {
    String getName();
    int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex);
}