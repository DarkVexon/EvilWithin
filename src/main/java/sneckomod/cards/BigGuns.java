package sneckomod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.BigGunsBlockPower;
import sneckomod.powers.BigGunsStrengthPower;

import java.util.ArrayList;

public class BigGuns extends AbstractSneckoCard {

    public final static String ID = makeID("BigGuns");

    //stupid intellij stuff POWER, SELF, RARE
    private static final int BASE_BLOCK = 6;
    private static final int UPG_BASE_BLOCK = 2;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public BigGuns() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BASE_BLOCK;
        SneckoMod.loadJokeCardImage(this, "BigGuns.png");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BigGunsBlockPower(this.baseBlock));
        applyToSelf(new BigGunsStrengthPower(this.magicNumber));
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.cost == 3);
            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeBlock(UPG_BASE_BLOCK);
        }
    }
}