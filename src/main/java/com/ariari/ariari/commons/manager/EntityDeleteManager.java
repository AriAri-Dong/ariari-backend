package com.ariari.ariari.commons.manager;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubbookmark.ClubBookmark;
import com.ariari.ariari.domain.club.deletedclub.DeletedClub;
import com.ariari.ariari.domain.club.deletedclub.DeletedClubRepository;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubpost.ClubPost;
import com.ariari.ariari.domain.recruitment.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class EntityDeleteManager {

    private final ClubRepository clubRepository;
    private final DeletedClubRepository deletedClubRepository;

    public void deleteClub(Club club) {
        // 하위 연관관계"들" 삭제 처리
        List<ClubMember> clubMembers = club.getClubMembers();
        for (ClubMember clubMember : clubMembers) {
            deleteClubMember(clubMember);
        }

        List<ClubBookmark> clubBookmarks = club.getClubBookmarks();
        for (ClubBookmark clubBookmark : clubBookmarks) {
            deleteClubBookmark(clubBookmark);
        }

        List<ClubPost> clubPosts = club.getClubPosts();
        for (ClubPost clubPost : clubPosts) {
            deleteClubPost(clubPost);
        }

        List<Recruitment> recruitments = club.getRecruitments();
        for (Recruitment recruitment : recruitments) {
            deleteRecruitment(recruitment);
        }

        // 엔티티 삭제 처리
        DeletedClub deletedClub = DeletedClub.fromClub(club);
        deletedClubRepository.save(deletedClub);
        clubRepository.delete(club);

    }

    public void deleteClubMember(ClubMember clubMember) {}

    public void deleteClubBookmark(ClubBookmark clubBookmark) {}

    public void deleteClubPost(ClubPost clubPost) {}

    public void deleteRecruitment(Recruitment recruitment) {}


}
