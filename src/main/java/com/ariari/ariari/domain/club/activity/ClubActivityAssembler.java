package com.ariari.ariari.domain.club.activity;

import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.activity.comment.like.ClubActivityCommentLike;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityCommentData;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityData;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityImageData;
import com.ariari.ariari.domain.club.activity.dto.res.ClubActivityDetailRes;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClubActivityAssembler {
    public static void assemble(ClubActivityDetailRes clubActivityDetailRes, ClubActivity clubActivity,
                                Map<ClubActivityComment, List<ClubActivityComment>> clubActivityCommentMap,
                                Map<ClubActivityComment, Integer> clubActivityLikeCountMap,
                                Map<ClubActivityComment, Set<Member>> clubActivityLikeMemberSetMap,
                                List<ClubActivityComment> clubActivityComments, List<ClubActivityCommentLike> clubActivityCommentLikes,
                                List<ClubActivityComment> parentClubActivityComments,
                                Member creatorMember, int likeCount, int commentCount,
                                Map<Member, ClubMember> clubMemberMap, Set<Member> commentsOfMembers
                                ){
        ClubActivityData clubActivityData = ClubActivityData.fromEntity(clubActivity, clubMemberMap, creatorMember,
                ClubActivityImageData.toEntityList(clubActivity.getClubActivityImages()),
                likeCount, false, commentCount); /// 활동후기 데이터 세팅
        clubActivityDetailRes.setClubActivityData(clubActivityData); /// 활동후기 상세res에 setter

        for(ClubActivityComment clubActivityComment : parentClubActivityComments){
            Member commentCreatorMember = clubActivityComment.getMember();
            ClubMember commentCreatorClubMember = null;
            if(commentCreatorMember != null){
                commentCreatorClubMember = clubMemberMap.get(commentCreatorMember);
            }

            ClubActivityDetailRes.ClubActivityCommentRes clubActivityCommentRes = new ClubActivityDetailRes.ClubActivityCommentRes();

            clubActivityCommentRes.setCommentData(ClubActivityCommentData.fromEntity(clubActivityComment, commentCreatorClubMember, commentCreatorMember,
                    clubActivityLikeCountMap.get(clubActivityComment), false, false));

            List<ClubActivityCommentData> childClubActivityCommentDataList = new ArrayList<>();
            if(clubActivityCommentMap.containsKey(clubActivityComment)){
                List<ClubActivityComment> childClubActivityComments = clubActivityCommentMap.get(clubActivityComment);
                for(ClubActivityComment childClubActivityComment : childClubActivityComments){
                    Member childCommentCreatorMember = childClubActivityComment.getMember();
                    ClubMember childCommentCreatorClubMember = null;
                    if(childCommentCreatorMember != null){
                        childCommentCreatorClubMember = clubMemberMap.get(childCommentCreatorMember);
                    }

                    ClubActivityCommentData childClubActivityCommentData = ClubActivityCommentData.fromEntity(childClubActivityComment, childCommentCreatorClubMember, childCommentCreatorMember,
                            clubActivityLikeCountMap.get(childClubActivityComment), false, false);
                    childClubActivityCommentDataList.add(childClubActivityCommentData);
                }
                clubActivityCommentRes.setCommentDataList(childClubActivityCommentDataList);
            }
            clubActivityDetailRes.getClubActivityCommentResList().add(clubActivityCommentRes);
        }
    }

    public static void assemble(ClubActivityDetailRes clubActivityDetailRes, ClubActivity clubActivity,
                                Map<ClubActivityComment, List<ClubActivityComment>> clubActivityCommentMap,
                                Map<ClubActivityComment, Integer> clubActivityLikeCountMap,
                                Map<ClubActivityComment, Set<Member>> clubActivityLikeMemberSetMap,
                                List<ClubActivityComment> clubActivityComments, List<ClubActivityCommentLike> clubActivityCommentLikes,
                                List<ClubActivityComment> parentClubActivityComments,
                                Member creatorMember, int likeCount, int commentCount,
                                Map<Member, ClubMember> clubMemberMap, Set<Member> commentsOfMembers,
                                Member reqMember, boolean isMyLiked, Set<Member> blockSet){
        ClubActivityData clubActivityData = ClubActivityData.fromEntity(clubActivity, clubMemberMap, creatorMember,
                ClubActivityImageData.toEntityList(clubActivity.getClubActivityImages()),
                likeCount, false, commentCount); /// 활동후기 데이터 세팅
        clubActivityDetailRes.setClubActivityData(clubActivityData); /// 활동후기 상세res에 setter

        for(ClubActivityComment clubActivityComment : parentClubActivityComments){
            Member commentCreatorMember = clubActivityComment.getMember();
            ClubMember commentCreatorClubMember = null;
            if(commentCreatorMember != null){
                commentCreatorClubMember = clubMemberMap.get(commentCreatorMember);
            }

            ClubActivityDetailRes.ClubActivityCommentRes clubActivityCommentRes = new ClubActivityDetailRes.ClubActivityCommentRes();

            clubActivityCommentRes.setCommentData(ClubActivityCommentData.fromEntity(clubActivityComment, commentCreatorClubMember, commentCreatorMember,
                    clubActivityLikeCountMap.get(clubActivityComment), clubActivityLikeMemberSetMap.get(clubActivityComment).contains(reqMember),
                    blockSet.contains(clubActivityComment.getMember()), reqMember));

            List<ClubActivityCommentData> childClubActivityCommentDataList = new ArrayList<>();
            if(clubActivityCommentMap.containsKey(clubActivityComment)){
                List<ClubActivityComment> childClubActivityComments = clubActivityCommentMap.get(clubActivityComment);
                for(ClubActivityComment childClubActivityComment : childClubActivityComments){
                    Member childCommentCreatorMember = childClubActivityComment.getMember();
                    ClubMember childCommentCreatorClubMember = null;
                    if(childCommentCreatorMember != null){
                        childCommentCreatorClubMember = clubMemberMap.get(childCommentCreatorMember);
                    }
                    ClubActivityCommentData childClubActivityCommentData = ClubActivityCommentData.fromEntity(childClubActivityComment, childCommentCreatorClubMember,
                            childCommentCreatorMember,
                            clubActivityLikeCountMap.get(childClubActivityComment), clubActivityLikeMemberSetMap.get(childClubActivityComment).contains(reqMember),
                            blockSet.contains(childClubActivityComment.getMember()), reqMember);
                    childClubActivityCommentDataList.add(childClubActivityCommentData);
                }
                clubActivityCommentRes.setCommentDataList(childClubActivityCommentDataList);
            }
            clubActivityDetailRes.getClubActivityCommentResList().add(clubActivityCommentRes);
        }
    }
}
