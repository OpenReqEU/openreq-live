package eu.openreq.service;

import eu.openreq.dbo.DelegateUserRequirementVoteDbo;
import eu.openreq.dbo.RequirementDbo;
import eu.openreq.dbo.UserDbo;
import eu.openreq.exception.DelegationCircleException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DelegateUserRequirementVoteService {

    public Map<Long, Map<String, Object>> extractUserVoteForwardDelegations(RequirementDbo requirement) {
        Map<Long, Map<String, Object>> userVoteForwardDelegations = new HashMap<>();
        for (DelegateUserRequirementVoteDbo delegation : requirement.getUserDelegationVotes()) {
            Map<String, Object> userInfo = new HashMap<>();
            UserDbo delegatedUser = delegation.getDelegatedUser();
            UserDbo user = delegation.getUser();
            userInfo.put("id", delegatedUser.getId());
            userInfo.put("firstName", delegatedUser.getFirstName());
            userInfo.put("lastName", delegatedUser.getLastName());
            userInfo.put("mailAddress", delegatedUser.getMailAddress());
            userInfo.put("profileImagePath", delegatedUser.getProfileImagePath());
            userVoteForwardDelegations.put(user.getId(), userInfo);
        }
        return userVoteForwardDelegations;
    }

    public Map<Long, List<Map<String, Object>>> extractUserVoteBackwardDelegations(RequirementDbo requirement) {
        Map<Long, List<Map<String, Object>>> userVoteBackwardDelegations = new HashMap<>();
        for (DelegateUserRequirementVoteDbo delegation : requirement.getUserDelegationVotes()) {
            Map<String, Object> userInfo = new HashMap<>();
            UserDbo delegatedUser = delegation.getDelegatedUser();
            UserDbo user = delegation.getUser();
            userInfo.put("id", user.getId());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            userInfo.put("mailAddress", user.getMailAddress());
            userInfo.put("profileImagePath", user.getProfileImagePath());
            List<Map<String, Object>> backwardDelegationsOfDelegatedUser = userVoteBackwardDelegations.get(delegatedUser.getId());
            if (backwardDelegationsOfDelegatedUser == null) {
                backwardDelegationsOfDelegatedUser = new ArrayList<>();
            }
            backwardDelegationsOfDelegatedUser.add(userInfo);
            userVoteBackwardDelegations.put(delegatedUser.getId(), backwardDelegationsOfDelegatedUser);
        }
        return userVoteBackwardDelegations;
    }

    public boolean isCircleInDelegationChain(RequirementDbo requirement, UserDbo currentUser, UserDbo delegatedUser) {
        Map<Long, List<Map<String, Object>>> backwardDelegations = extractUserVoteBackwardDelegations(requirement);

        if (backwardDelegations != null) {
            long delegatedUserID = delegatedUser.getId();
            Set<Long> visitedUserIDs = new HashSet<>();
            visitedUserIDs.add(delegatedUserID);

            if (visitedUserIDs.contains(currentUser.getId())) {
                return true;
            }
            visitedUserIDs.add(currentUser.getId());

            try {
                delegationChainDepthFirstSearch(backwardDelegations.get(currentUser.getId()), visitedUserIDs, backwardDelegations);
            } catch (DelegationCircleException e) {
                return true;
            }
        }
        return false;
    }

    public void delegationChainDepthFirstSearch(List<Map<String, Object>> receivingDelegations, Set<Long> visitedUserIDs,
                                                Map<Long, List<Map<String, Object>>> backwardDelegations) throws DelegationCircleException {
        if (receivingDelegations == null) {
            return;
        }

        for (Map<String, Object> receivingDelegation : receivingDelegations) {
            Long delegatingUserID = (Long)receivingDelegation.get("id");
            assert(delegatingUserID != null);

            if (visitedUserIDs.contains(delegatingUserID)) {
                throw new DelegationCircleException();
            }

            visitedUserIDs.add(delegatingUserID);
            delegationChainDepthFirstSearch(backwardDelegations.get(delegatingUserID), visitedUserIDs, backwardDelegations);
        }
    }

}
