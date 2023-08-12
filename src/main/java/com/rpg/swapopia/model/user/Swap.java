package com.rpg.swapopia.model.user;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;
import com.rpg.swapopia.model.item.Item;

@Table(name = "swap")
@Entity(name = "swap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Swap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User proposer;  // Usuário que propôs a troca

    @ManyToOne
    private User recipient; // Usuário que deve aceitar a troca

    @OneToMany
    private List<Item> proposerItems; // Itens propostos pelo propositor

    @OneToMany
    private List<Item> recipientItems; // Itens propostos pelo destinatário

    @Enumerated(EnumType.STRING)
    private SwapStatus status; // Enum: PENDING, ACCEPTED, REJECTED

}
