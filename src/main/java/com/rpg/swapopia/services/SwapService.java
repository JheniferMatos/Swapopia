package com.rpg.swapopia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.rpg.swapopia.domain.item.Item;
import com.rpg.swapopia.domain.user.Swap;
import com.rpg.swapopia.domain.user.User;
import com.rpg.swapopia.repositories.SwapRepository;




@Service
public class SwapService {
    @Autowired
    private SwapRepository swapRepository;

    public Swap proposeSwap(User proposer, User recipient, List<Item> proposerItems, List<Item> recipientItems) {
        Swap swap = new Swap();
        swap.setProposer(proposer);
        swap.setRecipient(recipient);
        swap.setProposerItems(proposerItems);
        swap.setRecipientItems(recipientItems);
        swap.setAccepted(false);
        return swapRepository.save(swap);
    }

    public void acceptSwap(Swap swap) {
        swap.setAccepted(true);
        swapRepository.save(swap);
    }
}
