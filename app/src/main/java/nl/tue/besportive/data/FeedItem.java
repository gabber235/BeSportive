package nl.tue.besportive.data;

public class FeedItem {
    private CompletedChallenge challenge;
    private Group.Member member;

    public FeedItem() {
    }

    public FeedItem(CompletedChallenge challenge, Group.Member member) {
        this.challenge = challenge;
        this.member = member;
    }

    public CompletedChallenge getChallenge() {
        return challenge;
    }

    public Group.Member getMember() {
        return member;
    }
}
