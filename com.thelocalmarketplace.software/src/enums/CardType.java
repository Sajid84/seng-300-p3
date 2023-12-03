package enums;

public enum CardType {
    CREDIT,
    DEBIT,
    MEMBERSHIP;

    public String toString() {
        return switch (this) {
            case CREDIT -> "credit";
            case DEBIT -> "debit";
            case MEMBERSHIP -> "membership";
        };
    }

}
