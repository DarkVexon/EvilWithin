package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import sneckomod.cards.Cower;
import sneckomod.cards.RiskySword;
import sneckomod.powers.BlunderGuardPower;
import sneckomod.powers.MudshieldPower;
import sneckomod.relics.CleanMud;
import sneckomod.relics.CrystallizedMud;
import sneckomod.relics.LoadedDie;

import java.util.ArrayList;

public class MuddleMarkedAction extends AbstractGameAction {

    private AbstractCard card;
    private boolean no3;

    public MuddleMarkedAction(AbstractCard bruhCard, boolean modified) {
        card = bruhCard;
        this.no3 = modified;
    }

    public MuddleMarkedAction(AbstractCard bruhCard) {
        this(bruhCard, false);
    }

    public void update() {
        isDone = true;

        if ((card instanceof RiskySword)) {
            ((RiskySword) card).onMuddledSword();
        }
        if ((card instanceof Cower)) {
            ((Cower) card).onMuddledSword();
        }


        if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {
            if (AbstractDungeon.player.hasPower(MudshieldPower.POWER_ID)) {
                AbstractDungeon.player.getPower(MudshieldPower.POWER_ID).onSpecificTrigger();
            }

            LoadedDie loadedDieInstance = new LoadedDie();
            if (AbstractDungeon.player.hasRelic(LoadedDie.ID)) {
                addToBot(new GainBlockAction(AbstractDungeon.player, 1));
                loadedDieInstance.flash();
            }

            card.superFlash();
            ArrayList<Integer> numList = new ArrayList<>();
            numList.add(0); // Always include zero as an option for free-to-play

            // Add costs that are strictly lower than the current cost for turn
            if (card.costForTurn > 1) numList.add(1);
            if (card.costForTurn > 2) numList.add(2);
            if (card.costForTurn > 3 && !AbstractDungeon.player.hasRelic(CleanMud.ID) && !this.no3) numList.add(3);

            int newCost = numList.get(AbstractDungeon.cardRandomRng.random(numList.size() - 1));
            if (card.costForTurn != newCost) {
                card.setCostForTurn(newCost);
            }

            card.freeToPlayOnce = false;
        }
    }
}