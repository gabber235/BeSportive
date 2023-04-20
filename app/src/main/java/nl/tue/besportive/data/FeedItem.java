package nl.tue.besportive.data;

import nl.tue.besportive.data.Group.Member;

public class FeedItem {
    private final CompletedChallenge challenge;
    private final Member member;

    public FeedItem(CompletedChallenge challenge, Member member) {
        this.challenge = challenge;
        this.member = member;
    }

    public CompletedChallenge getChallenge() {
        return challenge;
    }

    public Member getMember() {
        return member;
    }
}
