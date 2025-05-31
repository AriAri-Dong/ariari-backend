package com.ariari.ariari.domain.club.activity;

import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.activity.comment.like.ClubActivityCommentLike;
import com.ariari.ariari.domain.member.Member;

import java.util.*;

public class ClubActivityUtils {
    public static void initializeFindClubActivityMap(Map<ClubActivityComment, List<ClubActivityComment>> clubActivityCommentMap,
                                                 Map<ClubActivityComment, Integer> clubActivityLikeCountMap,
                                                 Map<ClubActivityComment, Set<Member>> clubActivityLikeMemberSetMap,
                                                 List<ClubActivityComment> clubActivityComments,
                                                 List<ClubActivityCommentLike> clubActivityCommentLikes){
        /// 댓글 데이터 맵
        for(ClubActivityComment clubActivityComment : clubActivityComments){
            clubActivityLikeCountMap.putIfAbsent(clubActivityComment, 0);
            clubActivityLikeMemberSetMap.putIfAbsent(clubActivityComment, new HashSet<>());
            if(clubActivityComment.getParentComment() == null){
                clubActivityCommentMap.putIfAbsent(clubActivityComment, new ArrayList<>());
            }
            else {
                clubActivityCommentMap.get(clubActivityComment.getParentComment()).add(clubActivityComment);
            }
        }

        /// 좋아요 데이터 맵
        for(ClubActivityCommentLike clubActivityCommentLike : clubActivityCommentLikes){
            clubActivityLikeCountMap.putIfAbsent(clubActivityCommentLike.getClubActivityComment(), 0);
            clubActivityLikeCountMap.put(clubActivityCommentLike.getClubActivityComment(), clubActivityLikeCountMap.get(clubActivityCommentLike.getClubActivityComment()) + 1);
            clubActivityLikeMemberSetMap.putIfAbsent(clubActivityCommentLike.getClubActivityComment(), new HashSet<>());
            clubActivityLikeMemberSetMap.get(clubActivityCommentLike.getClubActivityComment()).add(clubActivityCommentLike.getMember());
        }
    }


}
