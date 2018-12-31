package hu.elte.progtech.spynet.domain.character;

/**
 * This enum class represents that the character lives or has died already.
 */
public enum CharacterStatus {
    DEAD("dead"), ALIVE("alive");

    private final String status;

    CharacterStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static CharacterStatus getCharacterStatus(String status) {
        if (CharacterStatus.ALIVE.getStatus().equals(status)) {
            return CharacterStatus.ALIVE;
        } else if (CharacterStatus.DEAD.getStatus().equals(status)) {
            return CharacterStatus.DEAD;
        } else {
            throw new IllegalArgumentException("Illegal status: " + status + " status can be: 'alive'/'dead'");
        }
    }
}
