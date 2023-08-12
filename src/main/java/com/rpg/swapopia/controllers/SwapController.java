package com.rpg.swapopia.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpg.swapopia.model.item.Item;
import com.rpg.swapopia.model.user.Swap;
import com.rpg.swapopia.model.user.SwapProposalRequest;
import com.rpg.swapopia.model.user.SwapStatus;
import com.rpg.swapopia.model.user.User;
import com.rpg.swapopia.repositories.ItemRepository;
import com.rpg.swapopia.repositories.SwapRepository;
import com.rpg.swapopia.repositories.UserRepository;

@RestController
@RequestMapping("/troca")
public class SwapController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SwapRepository swapRepository;

    @PostMapping("/propostas")
    public ResponseEntity<String> proposeTrade(
            @RequestBody SwapProposalRequest swapProposalRequest,
            @AuthenticationPrincipal User authenticatedUser) {
        User proposer = authenticatedUser;
        User recipient = userRepository.findByLogin(swapProposalRequest.getRecipientLogin());

        if (recipient == null) {
            return ResponseEntity.notFound().build();
        }

        Set<String> proposerItemNames = new HashSet<>(swapProposalRequest.getProposerItemNames());
        Set<String> recipientItemNames = new HashSet<>(swapProposalRequest.getRecipientItemNames());

        List<Item> proposerItems = itemRepository.findByNameIn(new ArrayList<>(proposerItemNames));
        List<Item> recipientItems = itemRepository.findByNameIn(new ArrayList<>(recipientItemNames));

        if (proposerItems.isEmpty() || recipientItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Itens inválidos.");
        }

        Swap swap = new Swap();
        swap.setProposer(proposer);
        swap.setRecipient(recipient);
        swap.setProposerItems(proposerItems);
        swap.setRecipientItems(recipientItems);
        swap.setStatus(SwapStatus.PENDING);

        swapRepository.save(swap);

        return ResponseEntity.ok("Proposta de troca enviada com sucesso.");
    }

    @GetMapping("/propostas-recebidas")
    public ResponseEntity<List<Swap>> getReceivedSwapProposals(
            @AuthenticationPrincipal User authenticatedUser) {
        List<Swap> receivedProposals = swapRepository.findByRecipientAndStatus(authenticatedUser, SwapStatus.PENDING);
        return ResponseEntity.ok(receivedProposals);
    }

    @PostMapping("/aceitar-proposta/{proposalId}")
    public ResponseEntity<String> acceptSwapProposal(
            @PathVariable Long proposalId,
            @AuthenticationPrincipal User authenticatedUser) {
        Swap swap = swapRepository.findById(proposalId).orElse(null);

        if (swap == null || !swap.getRecipient().equals(authenticatedUser)) {
            return ResponseEntity.notFound().build();
        }

        swap.setStatus(SwapStatus.ACCEPTED);
        swapRepository.save(swap);

        // Atualize os inventários dos usuários aqui conforme a lógica da troca

        return ResponseEntity.ok("Proposta de troca aceita.");
    }

    @PostMapping("/recusar-proposta/{proposalId}")
    public ResponseEntity<String> rejectSwapProposal(
            @PathVariable Long proposalId,
            @AuthenticationPrincipal User authenticatedUser) {
        Swap swap = swapRepository.findById(proposalId).orElse(null);

        if (swap == null || !swap.getRecipient().equals(authenticatedUser)) {
            return ResponseEntity.notFound().build();
        }

        swap.setStatus(SwapStatus.REJECTED);
        swapRepository.save(swap);

        return ResponseEntity.ok("Proposta de troca recusada.");
    }
}
