// Liam Major 30223023

package managers.enums;

public enum PaymentType {
	CASH, CARD, CRYPTO;

	@Override
	public String toString() {
		return switch (this) {
		case CARD:
			yield "Card";
		case CASH:
			yield "Cash";
		case CRYPTO:
			yield "Crypto";
		};
	}
}
