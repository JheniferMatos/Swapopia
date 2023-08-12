package com.rpg.swapopia.model.user;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SwapProposalRequest {
    private String recipientLogin;
    private List<String> proposerItemNames;
    private List<String> recipientItemNames;

}