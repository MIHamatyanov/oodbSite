package oodbSite.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Client extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Bank bank;
    @OneToMany
    private List<BankAccount> bankAccounts;

    public void addBankAccount(BankAccount bankAccount) {
        if (this.bankAccounts == null) {
            this.bankAccounts = new ArrayList<>();
        }
        this.bankAccounts.add(bankAccount);
    }
}
