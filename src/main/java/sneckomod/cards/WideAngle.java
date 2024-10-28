package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class WideAngle extends AbstractSneckoCard {

    public final static String ID = makeID("WideAngle");

    private static final int COST = 3;
    private static final int DAMAGE = 18;
    private static final int UPGRADE_DMG = 6;

    public WideAngle() {
        super(ID, COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.retain = true; // Card is retained
        SneckoMod.loadJokeCardImage(this, "WideAngle.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deals damage to all enemies
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead && !monster.isDying) {
                addToBot(new DamageAction(monster, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DMG);
        }
    }
}
