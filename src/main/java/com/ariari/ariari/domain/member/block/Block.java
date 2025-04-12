package com.ariari.ariari.domain.member.block;


import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Block {

    @Id @CustomPkGenerate
    @Column(name = "block_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocking_member_id")
    private Member blockingMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_member_id")
    private Member blockedMember;

    public Block(Member blockingMember, Member blockedMember) {
        this.blockingMember = blockingMember;
        this.blockedMember = blockedMember;
    }
}
